##1、效果显示

![这里写图片描述](http://img.blog.csdn.net/20170307111217713?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvdTAxMjM5MTg3Ng==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

##2、实现分析
###1) 时钟外圆不变
画圆，只需要知道圆心的位置和半径即可。我们将画布的中心移到控件的中心
此时的圆心为(0,0) 
###2) 时钟刻度不变
小刻度每6度一个，大刻度每30度一个，先画小刻度，在画大刻度。
小刻度：角度从0-360度，每6度画一个小刻度 开始位置(小刻度长，0)-结束位置(圆半径,0),通过旋转画布（旋转6度），依照上述画法继续作画。
	   
大刻度：解读从0-360度，每30度画一个大刻度 开始位置(大刻度长，0)-结束位置(圆半径,0),通过旋转画布(旋转30度)，依照上述画法继续作画。
###3) 整点文本 位置不变
 将文本看做是放置到一个内圆中，如下图
![这里写图片描述](http://img.blog.csdn.net/20170307111450423?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvdTAxMjM5MTg3Ng==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

文本位置计算：

![这里写图片描述](http://img.blog.csdn.net/20170307111628908?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvdTAxMjM5MTg3Ng==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)
	       
###4) 当前时间 位置不变
**位置设置方式**：将文本放置到大圆中间的位置，如上图，根据这两行文本的宽高，对画的位置进行微调（保证两行文本不重合）

**位置计算**：
计算中间位置(圆半径/2,0)
年月日 x=-文本宽/2;y=圆半径/2-20;//将第一行文本上调20像素
 时分秒 x=-文本宽/2;y=圆半径/2+文本高;
 
### 5) 画时分秒针 -根据时间，显示出动态效果
**分析**
秒针随着当前时间秒数，每隔1s 转动一次
分针随着当前时间分数和秒数，进行转动 
 时针随着当前时间时数和分数，进行转动

**计算方式** 当前时间下的角度计算

![这里写图片描述](http://img.blog.csdn.net/20170307111948488?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvdTAxMjM5MTg3Ng==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)
     
###6) 时分秒针中间的点 --中心位置，(0,0)

##3、具体实现
直接上代码
     https://github.com/WhatWeCan/RoundClock/blob/master/app/src/main/java/com/tjstudy/roundclock/widget/RoundClockView.java
