package cool.hdd.domain.post.value


data class Product(
    val name: String,
    val price: Long,
    val link: String,
    val platformCategory: PlatformCategory,
)