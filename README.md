# miuibottomnavigation
仿小米应用商店底部按钮

# 效果


![录屏_选择区域_20211010145925.gif](https://p9-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/5627838ff4324b58be0e0ab070b59553~tplv-k3u1fbpfcp-watermark.image?)


# 用法
```xml
<com.hxl.miuibottomnavigation.BottomNavigationView
        android:layout_alignParentBottom="true"
        android:id="@+id/bottom"
        android:layout_width="match_parent"
        android:layout_height="70dp">
</com.hxl.miuibottomnavigation.BottomNavigationView>

```
```java
 findViewById<BottomNavigationView>(R.id.bottom)
        .setClickListener(object : IItemClickListener {
        override fun click(index: Int) {
        }
        })
        .init(
        NavigationBuild.Builder(this)
        .addItem("首页", R.drawable.ic_home)
        .addItem("娱乐", R.drawable.ic_game)
        .addItem("我的", R.drawable.ic_me)
        .setMode(Mode.MODE_SCROLL)
        .setSelectTextColor(Color.RED)
        .setFixedItems(mutableSetOf(1))
        .build()
        )
```
