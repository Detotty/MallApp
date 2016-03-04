package com.mallapp.Constants;

/**
 * Created by Sharjeel on 24/02/2016.
 */
public class GlobelWebURLs {
    //test server
    public static final String URL_KEY = "https://crowdeyews-test.azurewebsites.net/WCFService.svc/";
    //Production Server
//	public static final String URL_KEY 	="https://crowdeyesprod.azurewebsites.net/WCFService.svc/";
    //XMPP server
    public static final String IM_SERVER = "ec2-52-29-132-13.eu-central-1.compute.amazonaws.com";
    //CE_user
//	public static final String ce_user = "ce_";
    //CE_test_user
    public static final String ce_user = "cetest_";
    //CE_Company
//	public static final String ce_company =	"crowdeyes";
    //CE_test_company
    public static final String ce_company = "crowdeyestesting";
    //CE_group_ref
    public static final String ce_group = "@conference.ec2-52-29-132-13.eu-central-1.compute.amazonaws.com";
    //CE_claim
    public static final String ce_claim = "ce_claim_";


    public static final String SEND_SMS_URL_KEY 				=	URL_KEY+"SendValidationCode";
    public static final String VERIFY_SMS_URL_KEY 				=	URL_KEY+"ValidatePhoneNumber";
    public static final String GET_USER_PROFILE_URL_KEY 		=	URL_KEY+"GetProfile";
    public static final String UPDATE_USERP_ROFILE_URL_KEY 		=	URL_KEY+"SignUp";
    public static final String GET_CATEGORY_TREE_URL_KEY 		=	URL_KEY+"GetCategoryTree";
    public static final String GET_ALL_CHARACTERISTIC_DATATYPE 	=	URL_KEY+"GetAllCharacteristicDataTypes";
    public static final String CREATE_ANNOUNCEMENT 				=	URL_KEY+"CreateAnnouncement";
    public static final String GET_LATEST_ANNOUNCEMENT 			=	URL_KEY+"GetLatestAnnouncements";
    public static final String POST_ANNOUNCEMENT_PICTURE 		=	URL_KEY+"AddPictureToAnnouncement";
    public static final String BOOKMARKED_ANNOUNCEMENT 			=	URL_KEY+"BookmarkAnnouncement";
    public static final String BOOKMARKED_ANNOUNCEMENT_REMOVE 	=	URL_KEY+"RemoveBookmarkedAnnouncement";
    public static final String FAVORITE_ANNOUNCEMENT 			=	URL_KEY+"AddCategoryToFavorites";
    public static final String FAVORITE_ANNOUNCEMENT_REMOVE 	=	URL_KEY+"RemoveCategoryFromFavorites";
    public static final String GET_BOOKMARKED_ANNOUNCEMENTS 	=	URL_KEY+"GetBookmarkedAnnouncements";
    public static final String BLOCK_USER					 	=	URL_KEY+"BlockProfile";
    public static final String UN_BLOCK_USER					=	URL_KEY+"UnblockProfile";
    public static final String REPORT_USER						=	URL_KEY+"Report";
    public static final String GET_BLOCKED_USERS				=	URL_KEY+"GetBlockedProfiles";
    public static final String CREATE_CLAIM						=	URL_KEY+"CreateClaim";
    public static final String UPDATE_CLAIM						=	URL_KEY+"UpdateClaim";
    public static final String GET_COMMENTS						=	URL_KEY+"GetAnnouncementComments";
    public static final String POST_COMMENTS					=	URL_KEY+"CommentAnnouncement";
    public static final String GET_MY_CLAIMS                    =	URL_KEY+"GetMyClaims";
    public static final String GET_MY_ANNOUNCEMENTS             =	URL_KEY+"GetMyAnnouncements";
    public static final String GET_ANNOUNCEMENT_BY_IDS          =	URL_KEY+"GetAnnouncementsByIDs";
    public static final String GET_RELATED_ANNOUNCEMENTS        =	URL_KEY+"GetRelatedAnnouncements";
    public static final String CANCEL_ANNOUNCEMENT              =	URL_KEY+"CancelAnnouncement";
    public static final String HANDOVER_ANNOUNCEMENT            =	URL_KEY+"HandoverAnnouncement";
    public static final String ACCEPT_CLAIM                     =	URL_KEY+"ApproveClaim";
    public static final String REJECT_CLAIM                     =	URL_KEY+"RejectClaim";
    public static final String DELIVER_CLAIM                    =	URL_KEY+"DeliverAnnouncement";
    public static final String UPDATE_ANNOUNCEMENT 				=	URL_KEY+"UpdateAnnouncement";
    public static final String REMOVE_ANNOUNCEMENT_PICTURE 		=	URL_KEY+"RemovePictureFromAnnouncement";
    public static final String CANCEL_CLAIM 		            =	URL_KEY+"CancelClaim";
    public static final String GET_CE_USERS 		            =	URL_KEY+"GetApplicationProfiles";
    public static final String UPDATE_DEVICE_TOKEN 		        =	URL_KEY+"UpdateDeviceToken";
    public static final String GET_NOTIFICATION_SETTINGS 		=	URL_KEY+"GetNotificationSetting";
    public static final String ENABLE_NOTIFICATIONS 		    =	URL_KEY+"EnableNotifications";
    public static final String DISABLE_NOTIFICATIONS 		    =	URL_KEY+"DisableNotifications";
    public static final String START_CONV_WITH_USER 			=	URL_KEY+"StartConversationWithUser";
    public static final String GET_NEW_CONV_MSG 				=	URL_KEY+"GetNewConversationMessages";
    public static final String GET_CONV_MSG 				    =	URL_KEY+"GetConversationMessages";
    public static final String SEND_MSG 					    =	URL_KEY+"SendMessageToConversation";
    public static final String GET_MY_CONV 				   		=	URL_KEY+"GetMyConversations";
    public static final String ADD_PARTICIPANTS 				=	URL_KEY+"AddParticipantToConversation";
    public static final String LEAVE_CONV	 					=	URL_KEY+"LeaveConversation";
    public static final String NOTIFICATION_RECEIVED	 		=	URL_KEY+"NotificationReceived";
    public static final String NOTIFICATION_UNREAD	 			=	URL_KEY+"GetUnreadNotifications";
    public static final String NOTIFICATION_CLEAR	 			=	URL_KEY+"ClearNotifications";
    public static final String GET_PLAY_STORE_LINK	 			=	URL_KEY+"GetPlayStoreDownloadLink";
    public static final String FEED_BACK	 					=	URL_KEY+"SaveFeedback";
    public static final String GET_OTHER_PROFILE	 			=	URL_KEY+"GetOhterProfile";
}
