AbstractViewAdapter
===================

List adapter "injection" library which uses annotation processing to generate code that implements a ListView Adapter and assignes it to one of your ListViews. Also provides means to implement a ViewHolder pattern. 

```java
@ListLayout(R.layout.listitem_account)
public class Account {

  protected String number;
  protected Bitmap icon;

  @ListElement(R.id.image) public Bitmap getIcon() {
    return icon;
  }

  @ListElement(R.id.accountNumber) public String getNumber() {
    return number;
  }
}
    
public class MainActivity extends ActionBarActivity {
  @InjectList(R.id.list) AbstractViewAdapter<Account> adapter;
            
  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
                        
    AbstractViewAdapter.injectAdapters(this);
    //TODO use adapter to add items to the list
  }
}  
```
	
For documentation and additional information see the [Wiki][1].

##Download

There are two dependencies: abstractviewadapter-0.1.1.aar which should be included in your appliction's runtime and abstractviewadapter-compiler-0.1.1.jar which is used in compile time for code generation (contains java annotation processor).

Download the [latest compiler and library][2] or follow the steps below for Maven and Gradle projects.

For Maven projects, include abstractviewadapter depenency (runtime) in the dependencies section of pom.xml and abstractviewadapter-compiler dependency as an "optional" or "provided" dependency:

```xml
<dependencies>
  <dependency>
    <groupId>co.infinum</groupId>
    <artifactId>abstractviewadapter</artifactId>
    <version>0.1.1</version>
  </dependency>
  <dependency>
    <groupId>co.infinum</groupId>
    <artifactId>abstractviewadapter-compiler</artifactId>
    <version>0.1.1</version>
    <optional>true</optional>
  </dependency>
</dependencies>
```

For Gradle projects, add the following in the **dependencies** section:

```gradle
compile 'co.infinum:abstractviewadapter:0.1.1@aar'
provided 'co.infinum:abstractviewadapter:0.1.1@jar'
```

You can also use [android-apt][3] plugin instead of "provided".

##License
```
Copyright 2014 Ivan Kušt

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
```

[1]: https://github.com/ikust/AbstractViewAdapter/wiki
[2]: http://search.maven.org/#search%7Cga%7C1%7Cabstractviewadapter
[3]: https://bitbucket.org/hvisser/android-apt
