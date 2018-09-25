package mobile.liferay.com.formsscreenletdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import com.liferay.apio.consumer.ApioConsumer;
import com.liferay.apio.consumer.authenticator.BasicAuthenticator;
import com.liferay.apio.consumer.model.Thing;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.ddm.form.model.FormInstanceRecord;
import com.liferay.mobile.screens.ddm.form.service.APIOFetchLatestDraftService;
import com.liferay.mobile.screens.util.LiferayLogger;
import kotlin.Unit;
import mobile.liferay.com.formsscreenletdemo.util.Constants;
import mobile.liferay.com.formsscreenletdemo.util.FormsUtil;
import okhttp3.HttpUrl;

/**
 * @author Luísa Lima
 * @author Victor Oliveira
 */
public class HomeActivity extends AppCompatActivity {

	private Thing thing;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		Button formButton = findViewById(R.id.forms_button);
		formButton.setOnClickListener(this::startFormActivity);

		getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.login_status_bar_color));
		getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

		if (savedInstanceState == null) {
			checkForDraft();
		}
	}

	private void checkForDraft() {
		String server = getResources().getString(R.string.liferay_server);
		String url = FormsUtil.getResourcePath(server, Constants.FORM_INSTANCE_ID);
		HttpUrl httpUrl = HttpUrl.parse(url);

		try {
			String credentials = SessionContext.getCredentialsFromCurrentSession();
			new ApioConsumer(new BasicAuthenticator(credentials)).fetch(httpUrl, this::onThingLoaded, this::logError);
		} catch (Exception e) {
			LiferayLogger.e(e.getMessage());
		}
	}

	private Unit logError(Exception e) {
		LiferayLogger.e(e.getMessage());
		return Unit.INSTANCE;
	}

	private Unit onThingLoaded(Thing thing) {
		this.thing = thing;

		loadDraft(thing);

		return Unit.INSTANCE;
	}

	private void loadDraft(Thing thing) {
		new APIOFetchLatestDraftService().fetchLatestDraft(thing, this::onDraftLoaded, this::logError);
	}

	private Unit onDraftLoaded(Thing thing) {
		if (thing != null) {
			FormInstanceRecord formInstanceRecord = FormInstanceRecord.getConverter().invoke(thing);

			if (formInstanceRecord != null) {
				setupDialog();
			}
		}

		return Unit.INSTANCE;
	}

	private void setupDialog() {
		LayoutInflater inflater = getLayoutInflater();
		View dialogView = inflater.inflate(R.layout.custom_dialog, null);
		Button positiveButton = dialogView.findViewById(R.id.dialog_positive_button);
		Button negativeButton = dialogView.findViewById(R.id.dialog_negative_button);

		AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyAlertDialogStyle);
		builder.setView(dialogView);
		AlertDialog alertDialog = builder.create();

		negativeButton.setOnClickListener(v -> alertDialog.dismiss());
		positiveButton.setOnClickListener(view -> {
			alertDialog.dismiss();
			startFormActivity(view);
		});

		alertDialog.show();
	}

	private void startFormActivity(View view) {
		Intent intent = new Intent(HomeActivity.this, FormsActivity.class);
		startActivity(intent);
	}
}
