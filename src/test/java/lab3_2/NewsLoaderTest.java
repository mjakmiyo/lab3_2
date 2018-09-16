package lab3_2;

import static edu.iis.mto.staticmock.NewsReaderFactory.getReader;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import edu.iis.mto.staticmock.ConfigurationLoader;
import edu.iis.mto.staticmock.IncomingInfo;
import edu.iis.mto.staticmock.IncomingNews;
import edu.iis.mto.staticmock.NewsLoader;
import edu.iis.mto.staticmock.NewsReaderFactory;
import edu.iis.mto.staticmock.SubsciptionType;
import edu.iis.mto.staticmock.reader.NewsReader;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ ConfigurationLoader.class, NewsReaderFactory.class })
public class NewsLoaderTest {
	@Mock
	ConfigurationLoader configurationLoaderMock;
	IncomingNews incomingNews;
	@InjectMocks
	NewsLoader newsLoader;
	@Mock
	NewsReaderFactory newsReaderFactoryMock;
	@Mock
	NewsReader newsReaderMock;

	@Test
	public void loadNewsShouldGetReaderTypeFromConfiguration() {
		newsLoader.loadNews();
		verify(configurationLoaderMock, times(1)).loadConfiguration();
	}

	@Before
	public void setUp() {
		newsLoader = new NewsLoader();
		incomingNews = new IncomingNews();
		final IncomingInfo incomingPublicInfo = new IncomingInfo("Public Test", SubsciptionType.NONE);
		final IncomingInfo incomingSubcriptionAInfo = new IncomingInfo("Sub Test", SubsciptionType.A);
		incomingNews.add(incomingPublicInfo);
		incomingNews.add(incomingSubcriptionAInfo);
		mockStatic(ConfigurationLoader.class);
		configurationLoaderMock = mock(ConfigurationLoader.class);
		when(ConfigurationLoader.getInstance()).thenReturn(configurationLoaderMock);
		when(configurationLoaderMock.loadConfiguration()).thenReturn(new Configuration());
		newsReaderMock = mock(NewsReader.class);
		when(newsReaderMock.read()).thenReturn(incomingNews);
		mockStatic(NewsReaderFactory.class);
		newsReaderFactoryMock = mock(NewsReaderFactory.class);
		when(getReader(null)).thenReturn(newsReaderMock);
	}
}