package erp.mds.socialmediaback.constant;

public final class SocialConstant {

    public SocialConstant(){}

    public static final String STATUS_201 ="201";
    public static final String MESSAGE_201="Record Created Successfully";
    public static final String STATUS_200="200";
    public static final String MESSAGE_200="Request Processed Successfully";
    public static final String STATUS_417="417";
    public static final String MESSAGE_417_UPDATE="Update operation failed. Please try again later or contact Dev team";
    public static final String MESSAGE_417_DELETE="Delete operation failed. Please try again later or contact Dev team";
    public static final String STATUS_500="500";
    public static final String MESSAGE_500="An error occurred. Please try again later or contact Dev team";
    public static final Integer Post_PAGE_SIZE = 2;

    public static final String[] WHITE_LIST_URL = {
            "/api/v1/auth/**",
            "/v1/api/users/signup",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui/**",
            "/webjars/**",
            "/swagger-ui.html",
            "/swagger-ui/index.html",
            "v3/api-docs/**"
    };
}
