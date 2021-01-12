package cn.zxd.app.bean

data class Advertisement(val top: Top, val bottom: Bottom)

data class Bottom(val path: String)

data class Ad(val isRes: Boolean, val isVideo: Boolean, val resId: Int, val path: String)

data class Top(val adList: List<Ad>)