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

# 手动设置位置
```java
  findViewById<BottomNavigationView>(R.id.bottom).setCurrentItem(3)
```

# 关联ViewPager
```java


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        findViewById<ViewPager>(R.id.viewpager).adapter = object : PagerAdapter() {
            override fun getCount(): Int {
                return 3
            }

            override fun isViewFromObject(view: View, `object`: Any): Boolean {
                return view == `object`;
            }

            override fun instantiateItem(container: ViewGroup, position: Int): Any {
                var arr = arrayListOf<Int>(Color.RED, Color.BLUE, Color.YELLOW)
                var relativeLayout = RelativeLayout(this@MainActivity)
                relativeLayout.setBackgroundColor(arr[position])
                container.addView(relativeLayout)
                return relativeLayout;
            }

            override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
                container.removeView(`object` as View)
            }

        }
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
                    .setMode(Mode.MODE_NO_TITLE)
                    .setSelectTextColor(Color.RED)
                    .setupWithViewPager(findViewById(R.id.viewpager))
                    .setFixedItems(mutableSetOf(1))
                    .build()
            )

      
    }


}


```