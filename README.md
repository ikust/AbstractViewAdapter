AbstractViewAdapter
===================

List adapter "injection" library which uses annotation processing to generate code that implements a ListView Adapter and assignes it to one of your ListViews. Also provides means to implement a ViewHolder pattern. 


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
	
For documentation and additional information see the Wiki.

