package mobile.liferay.com.formsscreenletdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import com.liferay.mobile.screens.thingscreenlet.screens.ThingScreenlet;
import com.liferay.mobile.screens.thingscreenlet.screens.views.Detail;
import mobile.liferay.com.formsscreenletdemo.util.Constants;
import mobile.liferay.com.formsscreenletdemo.util.FormsUtil;

/**
 * @author Luísa Lima
 */
public class FormsActivity extends AppCompatActivity {

	private ThingScreenlet forms;
	private ProgressBar progressBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forms);

		forms = findViewById(R.id.forms);
		progressBar = findViewById(R.id.forms_progress_bar);

		if (savedInstanceState == null) {
			loadResource();
		}
	}

	private void loadResource() {
		String url =
			FormsUtil.getResourcePath(getResources().getString(R.string.liferay_server), Constants.FORM_INSTANCE_ID);

		progressBar.setVisibility(View.VISIBLE);
		forms.setVisibility(View.GONE);

		forms.load(url, Detail.INSTANCE, null, thingScreenlet -> {
			progressBar.setVisibility(View.GONE);
			forms.setVisibility(View.VISIBLE);

			return null;
		});
	}
}
