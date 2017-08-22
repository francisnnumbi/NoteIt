package fnn.smirl.noteit.fragments;
import android.os.*;
import android.support.v7.app.*;
import android.view.*;
import android.webkit.*;
import fnn.smirl.noteit.*;
import fnn.smirl.noteit.utils.*;

public class DisplayerFragment extends IconTitleFragment
{
	
	private WebView webView;
	
	public DisplayerFragment(AppCompatActivity activity, int iconId, String title){
		super(activity, iconId, title);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO: Implement this method
		return inflater.inflate(R.layout.displayer_fragment, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO: Implement this method
		super.onViewCreated(view, savedInstanceState);
		webView = (WebView) view.findViewById(R.id.displayerfragmentWebView1);
		setupWebview();
	}

	private void setupWebview() {
		WebSettings ws = webView.getSettings();
		ws.setJavaScriptEnabled(true);
		ws.setJavaScriptCanOpenWindowsAutomatically(true);
		ws.setDomStorageEnabled(true);
		ws.setLoadWithOverviewMode(true);
	
		webView.setWebViewClient(webClient);
		webView.setWebChromeClient(new WebChromeClient());

	}

	public void loadUrl(String url){
		webView.loadUrl(url);
		//webView.loadDataWithBaseURL("", url, "text/html", "utf-8", "");
	}
	
	public WebView getWebView(){
		return webView;
	}
	
	private WebViewClient webClient = new WebViewClient(){

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			// TODO: Implement this method
			return false;
		}
		
	};
	
	//private WebChromeClient chromeClient = new WebChromeClient(){
		
	//};
}
