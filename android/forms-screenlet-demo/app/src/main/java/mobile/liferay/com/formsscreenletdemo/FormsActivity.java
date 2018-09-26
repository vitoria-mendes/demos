package mobile.liferay.com.formsscreenletdemo;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.liferay.apio.consumer.model.Thing;
import com.liferay.mobile.screens.base.ModalProgressBarWithLabel;
import com.liferay.mobile.screens.thingscreenlet.screens.ThingScreenlet;
import com.liferay.mobile.screens.thingscreenlet.screens.events.ScreenletEvents;
import com.liferay.mobile.screens.thingscreenlet.screens.views.BaseView;
import com.liferay.mobile.screens.thingscreenlet.screens.views.Detail;
import com.liferay.mobile.screens.thingscreenlet.screens.views.Scenario;
import com.liferay.mobile.screens.util.AndroidUtil;
import com.liferay.mobile.screens.viewsets.defaultviews.ddm.events.FormEvents;
import kotlin.Unit;
import mobile.liferay.com.formsscreenletdemo.util.Constants;
import mobile.liferay.com.formsscreenletdemo.util.FormsUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Luísa Lima
 */
public class FormsActivity extends AppCompatActivity implements ScreenletEvents {

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
		formsScreenlet.setScreenletEvents(this);

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

	@Override
	public <T extends BaseView> void onCustomEvent(@NotNull String name, @NotNull ThingScreenlet screenlet,
		@Nullable T parentView, @NotNull Thing thing) {
		if (name.equals(FormEvents.SUBMIT_SUCCESS.name())) {
			LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
			View layout = inflater.inflate(R.layout.toast_layout_default, findViewById(R.id.toast_layout_default));

			Toast toast = new Toast(getApplicationContext());
			toast.setDuration(Toast.LENGTH_LONG);
			toast.setView(layout);
			toast.show();

			new Handler().postDelayed(this::finish, 500);
		}
	}

	@Nullable
	@Override
	public <T extends BaseView> View.OnClickListener onClickEvent(@NotNull T baseView, @NotNull View view,
		@NotNull Thing thing) {
		return null;
	}

	@Nullable
	@Override
	public <T extends BaseView> Integer onGetCustomLayout(@NotNull ThingScreenlet screenlet, @Nullable T parentView,
		@NotNull Thing thing, @NotNull Scenario scenario) {
		return null;
	}
}
