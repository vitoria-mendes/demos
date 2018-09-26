package mobile.liferay.com.formsscreenletdemo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import com.liferay.mobile.screens.base.ModalProgressBarWithLabel;
import com.liferay.mobile.screens.thingscreenlet.screens.ThingScreenlet;
import com.liferay.mobile.screens.thingscreenlet.screens.views.Detail;
import com.liferay.mobile.screens.util.AndroidUtil;
import kotlin.Unit;
import mobile.liferay.com.formsscreenletdemo.util.Constants;
import mobile.liferay.com.formsscreenletdemo.util.FormsUtil;

/**
 * @author Luísa Lima
 */
public class FormsActivity extends AppCompatActivity {

	private LinearLayout errorLayout;
	private ThingScreenlet formsScreenlet;
	private ModalProgressBarWithLabel progressBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forms);

		formsScreenlet = findViewById(R.id.forms_screenlet);
		errorLayout = findViewById(R.id.form_detail_error_view);
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
		formsScreenlet.setVisibility(View.GONE);

		formsScreenlet.load(url, Detail.INSTANCE, null, thingScreenlet -> {
			progressBar.hide();
			errorLayout.setVisibility(View.GONE);
			formsScreenlet.setVisibility(View.VISIBLE);

			return Unit.INSTANCE;
		}, e -> showError(e.getMessage()));
	}

	private Unit showError(String message) {
		progressBar.hide();
		errorLayout.setVisibility(View.VISIBLE);

		int icon = R.drawable.default_error_icon;
		int textColor = Color.WHITE;
		int backgroundColor =
			ContextCompat.getColor(this, com.liferay.mobile.screens.viewsets.lexicon.R.color.lightRed);

		AndroidUtil.showCustomSnackbar(formsScreenlet, message, Snackbar.LENGTH_LONG, backgroundColor, textColor, icon);

		return Unit.INSTANCE;
	}
}
