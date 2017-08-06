package com.example.android.hxpress.aciticties;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.android.hxpress.R;
import com.example.android.hxpress.managers.AppManager;
import com.example.android.hxpress.managers.UserManager;
import com.example.android.hxpress.models.ThingsWanted;
import com.example.android.hxpress.models.User;
import com.hdl.mricheditor.bean.CamaraRequestCode;
import com.hdl.mricheditor.view.MRichEditor;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobGeoPoint;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadBatchListener;


public class WritingThingWanted extends AppCompatActivity {
    private static final String IMG_URL = "FALSE_IMAGE:";  //文件存放的路径并用来正则匹配的开头字符串
    private static final int FINISH = 459;
    private static final int PREVIEW = 489;
    @BindView(R.id.title_textview)
    EditText titleTextview;
    @BindView(R.id.adddressname_textview)
    EditText adddressnameTextview;
    @BindView(R.id.adddress_textview)
    EditText adddressTextview;
    @BindView(R.id.where_thing_textview)
    EditText whereThingTextview;
    @BindView(R.id.mre_editor)
    MRichEditor mreEditor;
    @BindView(R.id.submit_button)
    FloatingActionButton submitButton;
    int ManifestType;
    //webView全局变量
    WebView wvPreiview;
    List<String> imageUrlFromBmob;

    //数据
    String contentInHTML;
    private MRichEditor editor;//编辑器

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_edit_thingwanted);
        ButterKnife.bind(this);
        //初始化Bmob
        Bmob.initialize(this, AppManager.appID);
        findViewById(R.id.submit_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFinished(v);
            }
        });
        //初始化编辑器
        initMRichEditor();

        //查找用户
        BmobQuery<User> query = new BmobQuery<User>();
        query.getObject("tA9Mbbbp", new QueryListener<User>() {
            @Override
            public void done(User object, BmobException e) {
                if (e == null) {
                    UserManager.loginedUser = object;
                } else {
                    Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
                }
            }

        });
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFinished(v);
                // Toast.makeText(WritingThingWanted.this, "提交成功！", Toast.LENGTH_LONG).show();
                // Log.v("!!!", "onClick: button");
            }
        });
    }


    /**
     * 初始化富文本编辑器
     */
    private void initMRichEditor() {
        editor = (MRichEditor) findViewById(R.id.mre_editor);
        editor.setServerImgDir(IMG_URL);//设置图片存放的路径
        editor.setOnPreviewBtnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ManifestType = PREVIEW;
                //上传图片至服务器
                uploadImg();
            }
        });

    }

    /**
     * 预览的布局
     */
    private void preview() {
        Log.v("preview", "journalTagsList");
        //绑定控件ID
        AlertDialog dialog = null;
        AlertDialog.Builder builder = new AlertDialog.Builder(WritingThingWanted.this);
        View view = View.inflate(WritingThingWanted.this, R.layout.dialog_preiview_html, null);
        wvPreiview = (WebView) view.findViewById(R.id.wv_dialog_preiview_html);
        ImageView ivClose = (ImageView) view.findViewById(R.id.iv_dialog_close);
        ImageView ivRefresh = (ImageView) view.findViewById(R.id.iv_dialog_refresh);
        ivRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wvPreiview.reload();
            }
        });
        builder.setView(view);
        dialog = builder.create();
        dialog.show();

        //设置AlertDialog的关闭事件
        final AlertDialog finalDialog = dialog;
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finalDialog.dismiss();
            }
        });

        //设置html的标题
        editor.setHtmlTitle(titleTextview.getText().toString().trim());

        //把编辑器中的HTML内容放置到webview中显示
        String htmlStr = editor.createHtmlStr();
        htmlStr = ModifyImageSrcFromHTML(htmlStr);

        //把html加载到webview中
        wvPreiview.loadData(htmlStr, "text/html; charset=UTF-8", null);
    }

    /**
     * 需要重写这个方法,并且加上下面的判断
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        ;
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) {
            Toast.makeText(WritingThingWanted.this, "取消操作", Toast.LENGTH_LONG).show();
            return;
        }
        if (requestCode == CamaraRequestCode.CAMARA_GET_IMG) {
            editor.insertImg(data.getData());
        } else if (requestCode == CamaraRequestCode.CAMARA_TAKE_PHOTO) {
            editor.insertImg(data);
        }
    }

    /**
     * 完成按钮---将文件和图片提交到服务器
     *
     * @param view
     */
    public void onFinished(View view) {
        ManifestType = FINISH;
        uploadImg();
    }

    public void UploadToDB() {

        ThingsWanted thing = new ThingsWanted();
        thing.setTitle(titleTextview.getText().toString().trim());

        //thing.setPoint(address);
        thing.setPoint(new BmobGeoPoint(21, 25));
        contentInHTML = ModifyImageSrcFromHTML(editor.createHtmlStr());

        thing.setDescription(contentInHTML);
        thing.setSend2address(adddressTextview.getText().toString().trim());
        thing.setSend2Name(adddressnameTextview.getText().toString().trim());
        thing.setProgressNum(0);

        if (imageUrlFromBmob.size() != 0 && imageUrlFromBmob != null) {
            thing.setTopImgUrls(imageUrlFromBmob.get(0));
        }
        //thing.setRecipients(UserManager.loginedUser);
        // User user = BmobUser.getCurrentUser(User.class);
        thing.setRecipients(UserManager.loginedUser);
        thing.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    Log.v("UploadToDB", "添加数据成功，返回objectId为：" + s);
                } else {
                    Log.v("UploadToDB", "创建数据失败：" + e.getMessage());
                }
            }
        });
    }

    /**
     * 上传图片(这里用于实时预览,上传了图片才可以预览哦,否则看不到图片,只能看见文字)
     */
    private void uploadImg() {
        if (editor.getImgPath().size() != 0) {
            final String[] filePaths = new String[editor.getImgPath().size()];
            for (int i = 0; i < editor.getImgPath().size(); i++) {
                filePaths[i] = editor.getImgPath().get(i);
            }
            Log.v("uploadImg", "editor.getImgPath() " + String.valueOf(editor.getImgPath().size()));
            BmobFile.uploadBatch(filePaths, new UploadBatchListener() {
                @Override
                public void onSuccess(List<BmobFile> files, List<String> urls) {
                    //1、files-上传完成后的BmobFile集合，是为了方便大家对其上传后的数据进行操作，例如你可以将该文件保存到表中
                    //2、urls-上传文件的完整url地址
                    Log.v("uploadImg", "一个！");
                    if (urls.size() == filePaths.length) {//如果数量相等，则代表文件全部上传完成
                        Log.v("uploadImg", "全部上传完毕");
                        imageUrlFromBmob = urls;
                        for (String item : urls) {
                            Log.v("onSuccess", item);
                        }
                        switch (ManifestType) {
                            case PREVIEW:
                                preview();
                                break;
                            case FINISH:
                                UploadToDB();
                                break;
                            default:
                                break;
                        }
                    }
                }

                @Override
                public void onError(int statuscode, String errormsg) {
                    // ToastUtil.show(WritingJournal.this,"错误码"+statuscode +",错误描述："+errormsg);
                }

                @Override
                public void onProgress(int curIndex, int curPercent, int total, int totalPercent) {
                    //1、curIndex--表示当前第几个文件正在上传
                    //2、curPercent--表示当前上传文件的进度值（百分比）
                    //3、total--表示总的上传文件数
                    //4、totalPercent--表示总的上传进度（百分比）
                }
            });
        } else {
            switch (ManifestType) {
                case PREVIEW:
                    preview();
                    break;
                case FINISH:
                    UploadToDB();
                    break;
                default:
                    break;
            }
        }
//        Log.e("MRichEditorDemo", editor.getHtmlStr());
    }

    /**
     * 打开帮助页面
     *
     * @param view
     */
    public void onHelp(View view) {
        Toast.makeText(this, "操作手册\n点击-->修改(图片除外),长按-->删除", Toast.LENGTH_LONG).show();
    }

    private String ModifyImageSrcFromHTML(String oldHTML) {
        String newCorrectHTML = oldHTML;
        //正则匹配
        Pattern pattern = Pattern.compile("\'FALSE_IMAGE(.*?).\'");// 匹配的模式
        if (imageUrlFromBmob != null) {
            for (String item : imageUrlFromBmob) {
                //替换为正确的地址
                Matcher matcher = pattern.matcher(newCorrectHTML);
                newCorrectHTML = matcher.replaceFirst("\'" + item + "\'");
            }
        }
        //返回正确的html
        Log.v("ModifyImageSrcFromHTML", newCorrectHTML);
        return newCorrectHTML;
    }
}
