# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in D:\sdk\android-sdk-windows/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
-keepclassmembers class ** {
    public void onEvent*(**);
}

#包明不混合大小写
-dontusemixedcaseclassnames

#不去忽略非公共的库类
-dontskipnonpubliclibraryclasses

#优化  不优化输入的类文件
-dontoptimize

#预校验
-dontpreverify
#混淆时是否记录日志
-verbose

# 混淆时所采用的算法
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*

# 保持哪些类不被混淆
-keep public class * extends android.app.Fragment
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class com.android.vending.licensing.ILicensingService
#如果有引用v4包可以添加下面这行
-keep public class * extends android.support.v4.app.Fragment

-keepattributes SourceFile,LineNumberTable

#忽略警告
-ignorewarning
##记录生成的日志数据,gradle build时在本项目根目录输出##
#apk 包内所有 class 的内部结构
-dump class_files.txt
#未混淆的类和成员
-printseeds seeds.txt
#列出从 apk 中删除的代码
-printusage unused.txt
#混淆前后的映射
-printmapping mapping.txt

#如果不想混淆 keep 掉
-keep class com.lippi.recorder.iirfilterdesigner.** {*; }
#友盟
-keep class com.umeng.**{*;}
#忽略警告
-dontwarn com.lippi.recorder.utils**
#保留一个完整的包
-keep class com.lippi.recorder.utils.** {
        *;
     }

-keep class  com.lippi.recorder.utils.AudioRecorder{*;}

#如果引用了v4或者v7包
-dontwarn android.support.**

#混淆保护自己项目的部分代码以及引用的第三方jar包library-end####

-keep public class * extends android.view.View {
      public <init>(android.content.Context);
      public <init>(android.content.Context, android.util.AttributeSet);
      public <init>(android.content.Context, android.util.AttributeSet, int);
      public void set*(...);
}

#保持 native 方法不被混淆
-keepclasseswithmembernames class * {
      native <methods>;
}

#保持自定义控件类不被混淆
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
         }

#保持自定义控件类不被混淆
-keepclassmembers class * extends android.app.Activity {
       public void *(android.view.View);
    }
# eventbus
# Only required if you use AsyncExecutor

#保持 Parcelable 不被混淆
-keep class * implements android.os.Parcelable {
     public static final android.os.Parcelable$Creator *;
   }

#保持 Parcelable 不被混淆
-keep class * implements android.os.Parcelable {
     public static final android.os.Parcelable$Creator *;
   }

#保持 Serializable 不被混淆
-keepnames class * implements java.io.Serializable

#保持 Serializable 不被混淆并且enum 类也不被混淆
-keepclassmembers class * implements java.io.Serializable {
        static final long serialVersionUID;
        private static final java.io.ObjectStreamField[] serialPersistentFields;
        !static !transient <fields>;
        !private <fields>;
        !private <methods>;
        private void writeObject(java.io.ObjectOutputStream);
        private void readObject(java.io.ObjectInputStream);
        java.lang.Object writeReplace();
        java.lang.Object readResolve();
    }

#保持枚举 enum 类不被混淆 如果混淆报错，建议直接使用上面的 -keepclassmembers class * implements java.io.Serializable即可
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}


#--------butterknife----------
-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }

-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}

-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}
#Q浏览器x5内核混淆
-keep class com.tencent.** {*;}
# 是否混淆第三方jar
-dontskipnonpubliclibraryclasses
-dontpreverify
-keepattributes SourceFile,LineNumberTable
-verbose
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
-keep public class * extends android.support.v4.app.Fragment

-keep class org.apache.http.*
-keep public class org.apache.commons.httpclient.** {*;}
-keep public class org.apache.commons.httpclient.auth.** {*;}
-keep public class org.apache.commons.httpclient.cookie.** {*;}
-keep public class org.apache.commons.httpclient.methods.** {*;}
-keep public class org.apache.commons.httpclient.params.** {*;}
-keep public class org.apache.commons.httpclient.util.** {*;}
-keep public class org.apache.commons.codec.net.** {*;}
-keep public class org.apache.commons.logging.** {*;}
-keep public class org.apache.commons.logging.impl.** {*;}
-keep public class org.apache.commons.codec.** {*;}
-keep public class org.apache.commons.codec.binary.** {*;}


-keep public class org.apache.http.conn.params.** {*;}
-keep public class org.apache.http.conn.params.*
-keepclassmembernames class org.apache.http.conn.params.** {*;}
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.stream.** { *; }
-keep class com.google.gson.examples.android.model.** { *; }

-keep class com.example.b2c.net.*
-keepclassmembers class com.example.b2c.net.**{
    *;
}
-keep class com.example.b2c.config.*
#不混淆android-async-http
-keep class com.loopj.android.http.**{*;}

 #不混淆org.apache.http.legacy.jar
 -dontwarn android.net.compatibility.**
 -dontwarn android.net.http.**
 -dontwarn com.android.internal.http.multipart.**
 -dontwarn org.apache.commons.**
 -dontwarn org.apache.http.**
 -keep class android.net.compatibility.**{*;}
 -keep class android.net.http.**{*;}
 -keep class com.android.internal.http.multipart.**{*;}
 -keep class org.apache.commons.**{*;}
 -keep class org.apache.http.**{*;}
-keep public class * extends android.preference.Preference
-keep class com.example.b2c.activity.web.**

#不混淆资源类
-keepclassmembers class **.R$* {
        public static <fields>;
}
-keepclassmembers class * {
    public void *ButtonClicked(android.view.View);
}
-keepattributes Signature

-dontwarn java.nio.file.*
-keep class com.example.b2c.widget.util.**
-keepclassmembers class com.example.b2c.widget.util.**{
    *;
}
-keep class com.example.b2c.widget.**
-keepclassmembers class com.example.b2c.widget.**{
    *;
}
-keep class com.example.b2c.net.response.*
-keepclassmembers class com.example.b2c.net.response.**{
    *;
}
-keep class com.example.b2c.net.bean.*
-keepclassmembers class com.example.b2c.net.bean.**{
    *;
}
-keep class com.example.b2c.net.listener.*
-keepclassmembers class com.example.b2c.net.listener.**{
    *;
}
-keep class com.example.b2c.net.zthHttp.*
-keepclassmembers class com.example.b2c.net.zthHttp.**{
    *;
}
-keep class com.example.b2c.zxing.*
-keepclassmembers class com.example.b2c.zxing.**{
    *;
}
-keep class com.example.b2c.temple.*
-keepclassmembers class com.example.b2c.temple.**{
    *;
}
-keep class com.jcodecraeer.*
-keepclassmembers class com.jcodecraeer.**{
    *;
}
##---------------Begin: proguard configuration for Gson  ----------
# Gson uses generic type information stored in a class file when working with fields. Proguard
# removes such information by default, so configure it to keep all of it.
-keepattributes Signature

# For using GSON @Expose annotation
-keepattributes *Annotation*

# Gson specific classes
-keep class sun.misc.Unsafe { *; }
#-keep class com.google.gson.stream.** { *; }

# Application classes that will be serialized/deserialized over Gson
-keep class com.google.gson.examples.android.model.** { *; }
-keep class com.google.gson.** { *;}

##---------------End: proguard configuration for Gson  ----------
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}
#okhttp的混淆
-dontwarn com.squareup.okhttp3.**
-keep class com.squareup.okhttp3.** { *;}
-dontwarn okio.**
# EventBus 混淆代码
-keepattributes *Annotation*
-keepclassmembers class ** {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}
-keepclassmembers class * extends de.greenrobot.event.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}
-keepclassmembers class * {
    void *(***Event);
}
# FastJson 混淆代码
-dontwarn com.alibaba.fastjson.**
-keep class com.alibaba.fastjson.** { *; }
-keepattributes Signature
-keepattributes *Annotation*
#webview混淆
-keepclassmembers class fqcn.of.javascript.interface.for.Webview {
   public *;
}
-keepclassmembers class * extends android.webkit.WebViewClient {
    public void *(android.webkit.WebView, java.lang.String, android.graphics.Bitmap);
    public boolean *(android.webkit.WebView, java.lang.String);
}
-keepclassmembers class * extends android.webkit.WebViewClient {
    public void *(android.webkit.WebView, jav.lang.String);
}
## nineoldandroids-2.4.0.jar
-keep public class com.nineoldandroids.** {*;}
# universal-image-loader 混淆
-dontwarn com.nostra13.universalimageloader.**
-keep class com.nostra13.universalimageloader.** { *; }
#adapter也不能混淆
-keep public class * extends android.widget.BaseAdapter {*;}
