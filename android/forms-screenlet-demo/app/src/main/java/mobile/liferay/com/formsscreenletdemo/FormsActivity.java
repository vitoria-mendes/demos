package mobile.liferay.com.formsscreenletdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import com.liferay.mobile.screens.base.ModalProgressBarWithLabel;
import com.liferay.mobile.screens.thingscreenlet.screens.ThingScreenlet;
import com.liferay.mobile.screens.thingscreenlet.screens.views.Detail;
import mobile.liferay.com.formsscreenletdemo.util.Constants;
import mobile.liferay.com.formsscreenletdemo.util.FormsUtil;

/**
 * @author Luísa Lima
 */
public class FormsActivity extends AppCompatActivity {

	private ThingScreenlet forms;
	private ModalProgressBarWithLabel progressBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forms);

		forms = findViewById(R.id.forms);
		progressBar = findViewById(R.id.liferay_modal_progress);
		progressBar.disableDimBackground();

		FormsUtil.setLightStatusBar(this, getWindow());

		if (savedInstanceState == null) {
			loadResource();
		}
	}

	private void loadResource() {
		String url =
			FormsUtil.getResourcePath(getResources().getString(R.string.liferay_server), Constants.FORM_INSTANCE_ID);

		progressBar.show(getString(R.string.loading_form));
		forms.setVisibility(View.GONE);

		forms.load(url, Detail.INSTANCE, null, thingScreenlet -> {
			progressBar.hide();
			forms.setVisibility(View.VISIBLE);

			return null;
		});
	}
}
