val minecraftVersion: String by extra
val modVersion: String by extra

allprojects {
    group = "de.salocin"
    version = "$minecraftVersion-$modVersion"

}
