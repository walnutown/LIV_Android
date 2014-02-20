LIV_Android
===========
##Background
LIV is a startup whose mission is to bring excitement and value to people’s daily experiences. We provide deal-of-the-day service that features discounted gift certificates usable at local or national companies. We combine Vine and Groupon to make shopping more interesting.<br>
At present, there’re lots of deal apps like Groupon, LivingSocial, AmazonLocal and deal- aggregator Yipit. Normally, these Groupon-like apps use picture flow to show deals, in LIV, we use video flow (like in Vine, a short 6 second video clip), which is a fantastic way to attract attention and increase interaction between deal provider and consumer.

##Project Progress
The app hasn't been finished yet.
  * Parts have been completed
      * sliding menu (Create a sliding menu using animation and drawables)
      * login-register module (json transfer between server and client, client-side local storage)
      * deal page (display a list of deals and automate the playback of video while the page is scrolled)
      * style the app using *res/style* and *XML drawables*

##Challenges:
  * Build the SlidingMenu
      * Mechanism:
         * slidingMenu is used to display user menus. It can be operated by clicking the thumb button on the home page or by gestures. When the slidingMenu is opened, it’s added to the rootView and slides right into the screen; when it is closed, it slides left and is removed from the rootView
      * Create the slidingMenu using animation(ObjectAnimator) and drawables. This also includes the manipulation of related views and creation of custom views. (HierarchyViewer)
      * Control the enabled state of layouts dynamically. When the slidingMenu is open,
the home page should be disabled and touch on the home page will close the slidingMenu; when the slidingMenu is closed, all layouts in the rootView are enabled again.
  * Create the video flow for displaying deals
     * Mechanism:
         * video flow features the home page, where all deals are displayed here. Each deal consists of a video and deal description.  
         * Clicking on the surface of the video can control the playback of the video.
         * Each video has a thumbnail( the first frame of the video), which is used to fill the blank before the video is completely downloaded and ready to play.
     * Create custom video view (combine TextureView and MediaPlayer). At first, I used the Android native VideoView, yet VideoView has rendering issues in the ListView. This is caused by the container of the VideoView --  SurfaceView. SurfaceView works by creating a new window placed behind the current window. It punches a hole through current window to reveal the new window. While this approach is very efficient, since the content of the new window can be refreshed without redrawing current window, it suffers in transformation(move, scale, rotate). This makes it difficult to use SurfaceView inside a ListView or ScrollView. The TextureView, introduced in Android 4.0, offers the same capabilities as SurfaceView, but behaves as a regular view. Therefore, I finally used TextureView as the video container and wrote a TextureViewListener to control the creation and destroy of the video container.
     * Download video in AsynTask
     * Manage MediaPlayer’s lifecycle. MediaPlayer is used to control playback of audio/videos. When the video is downloaded from the server, MediapLayer is created to play the video and when the video stops playing, MediaPlayer should be released clearly so as to save memory space.

##Others
Android is known for various versions. In this app, we initially set the minSDK as API8, which will cover the 98% of Android phones on the market. Yet, the minSDK leveled up when I built the project. In order to create the slidingMenu, I used the ObjectAnimator (API11), then in creating custom video view, I used TextureView(API18). So, I generally placed the priority of function implementation before the backward compatibility.
