# NextDay Client for Android

Next Day的Android客户端，使用的接口是[NextDay 非官方 Web API](https://github.com/sanddudu/nextday-desktop/wiki/API).

##### 目前只是一个简单的界面,展示当天的内容。使用的到技术有：

1. [Retrofit2 网络框架](https://github.com/square/retrofit)
2. [Android Architecture Components 架构](https://developer.android.com/topic/libraries/architecture/index.html)
3. [fresco 图片加载框架](https://github.com/facebook/fresco)
4. [butterknife](https://github.com/JakeWharton/butterknife)

##### 后续的开发计划为：

1. 权限动态检查申请。
2. 给图片、文字内容添加加载过程动画。
3. 支持左右滑动，显示前/后一天的内容。
4. 内容分享。
5. 滑动锁屏功能。
6. 缓存。
7. 日期选择功能，选择制定的日期，请求显示对应内容。

代码风格遵循阿里公布的Java规范，可在Android Studio中搜索安装[Alibaba Java Coding Guidelines](https://github.com/alibaba/p3c)