package cn.zxd.app.bean

data class Advertisement(
    val banner: Banner,
    val describe: String,
    val id: Long,
    val name: String,
    val order: Int,
    val page: Page
)

data class Banner(
    val url: String
)

data class Page(
    val image: List<String>,
    val imageInterval: Int,
    val type: String,
    val videoUrl: String
)