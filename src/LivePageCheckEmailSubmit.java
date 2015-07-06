import org.testng.Assert;
import org.testng.annotations.Test;

public class LivePageCheckEmailSubmit extends BasePage  {

	private HomePage home = new HomePage();
	
	@Test
	public void test()
	{
		home.GetStarted();
		Assert.assertEquals(home.getHeadingText(), "Select the photo you would like karol test to autograph");
	}
}
