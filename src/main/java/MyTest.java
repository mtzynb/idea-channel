import com.rahand.idea.channel.exception.CustomWebClientResponseException;

public class MyTest {
    public static void main(String[] args) {

//        WebClientInstance webClient = new WebClientInstance();
//        BoomService boomService = new BoomService(webClient);
//        System.out.println("**************** BEforeeeeeeee callll ****************");
//        System.out.println(boomService.getAccessToken().toString());
//        System.out.println("**************** AFTER callll ****************");

//        WebClientInstance webClient = new WebClientInstance();
//        BoomService boomService = new BoomService(webClient);
//        System.out.println("**************** BEforeeeeeeee callll ****************");
//        System.out.println(boomService.getDepositInfo().toString());
//        System.out.println("**************** AFTER callll ****************");

        try {
            Test test = new Test();
            System.out.println("**************** BEforeeeeeeee callll ****************");

            String res = test.getDepositDetails("b8370d68-7e8c-48ca-8591-92a52b7f01dd");
            System.out.println("**************** AFETR callll ****************");
            System.out.println("response: " + res);

        } catch (CustomWebClientResponseException e) {
            System.out.println("getLocalizedMessage: " + e.getLocalizedMessage());
            System.out.println("getMessage: " + e.getMessage());
            System.out.println("getStackTrace: " + e.getStackTrace());
            System.out.println("getCause: " + e.getCause());
            System.out.println("getDetails: " + e.getErrorDetails().toString());
            System.out.println("getStatus: " + e.getStatus());
            System.out.println("getResponseBodyAsString: " + e.getResponseBodyAsString());
//            System.out.println("getBankName: "+ e.getBankName());
        }
    }
}
