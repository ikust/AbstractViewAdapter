AbstractViewAdapter
===================

List adapter "injection" library which uses annotation processing to generate code that implements a ListView Adapter and assignes it to one of your ListViews. Also provides means to implement a ViewHolder pattern. 

```java
@ListLayout(R.layout.listitem_account)
public class Account {

  protected String number;
  protected Bitmap icon;

  @ListView(R.id.image) public Bitmap getIcon() {
    return icon;
  }

  @ListView(R.id.accountNumber) public String getNumber() {
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

#License

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
