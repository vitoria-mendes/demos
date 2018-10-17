package mobile.liferay.com.formsscreenletdemo.service;

import com.liferay.apio.consumer.authenticator.ApioAuthenticator;
import com.liferay.apio.consumer.authenticator.BasicAuthenticator;
import com.liferay.apio.consumer.model.Thing;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.ddm.form.service.BaseAPIOService;
import java.util.Collections;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import okhttp3.HttpUrl;

/**
 * @author Paulo Cruz
 */
public class APIOFetchResourceService extends BaseAPIOService {

	public void fetchResource(String thingId, Function1<Thing, Unit> onSuccess,
		Function1<Exception, Unit> onError) {

		HttpUrl httpUrl = HttpUrl.parse(thingId);

		ApioAuthenticator authenticator = null;

		try {
			authenticator = new BasicAuthenticator(SessionContext.getCredentialsFromCurrentSession());
		} catch (Exception e) {
			e.printStackTrace();
		}

		apioConsumer.fetch(httpUrl, authenticator, Collections.emptyMap(), Collections.emptyList(), result -> {
			result.fold(onSuccess, onError);

			return Unit.INSTANCE;
		});
	}

}
