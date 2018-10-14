# LoneyTags

### Integration
#### For maven:
```xml
<repositories>
  <repository>
    <id>zenegix-repo</id>
    <url>https://repo.zenegix.ru/content/groups/public/</url>
  </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>ru.zenegix.tags</groupId>
        <artifactId>tags-bukkit-plugin</artifactId>
        <version>1.0-SNAPSHOT</version>
        <scope>provided</scope>
    </dependency>
</dependencies>
```
#### For gradle:
```gradle
repositories {
  maven {
    url 'https://repo.zenegix.ru/content/groups/public/'
  }
}

dependencies {
    compileOnly 'ru.zenegix.tags:tags-bukkit-plugin:1.0-SNAPSHOT'
}
```

#### In plugin.yml:
```
depend: [LoneyTags]
softdepend: [LoneyTags]
```
