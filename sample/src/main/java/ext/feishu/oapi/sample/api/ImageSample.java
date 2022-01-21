package ext.feishu.oapi.sample.api;

import ext.feishu.oapi.core.AppSettings;
import ext.feishu.oapi.core.Config;
import ext.feishu.oapi.core.Domain;
import ext.feishu.oapi.core.api.request.FormDataFile;
import ext.feishu.oapi.core.api.response.Response;
import ext.feishu.oapi.core.api.tools.IOs;
import ext.feishu.oapi.core.utils.Jsons;
import ext.feishu.oapi.sample.config.Configs;
import ext.feishu.oapi.service.image.v4.ImageService;
import ext.feishu.oapi.service.image.v4.model.Image;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ImageSample {

    // for Cutome APP（自建应用）
    private static final AppSettings appSettings = Config.getInternalAppSettingsByEnv();
    // config with redis store
    // private static final Config config = Configs.getConfigWithRedisStore("https://open.feishu.cn", appSettings);
    // private static final Config config = Configs.getConfig("https://open.feishu.cn", appSettings);
    private static final Config      config      = Configs.getConfig(Domain.FeiShu, appSettings);

    public static void main(String[] args) throws Exception {
        //testPut();
        testGet();
        System.out.println("end");
    }

    private static void testPut() throws Exception {
        // config.getStore().put(Keys.tenantAccessTokenKey(config.getAppSettings().getAppID(), ""), "t-xxxxxxxxxxxxxxxxxxxxxxxxx", 1000, TimeUnit.DAYS);
        ImageService service = new ImageService(config);
        ImageService.ImagePutReqCall reqCall = service.getImages().put();
        try (InputStream inputStream = new FileInputStream("/tmp/file_name")) {
            // method 1: use byte stream
            // reqCall.setImage(new FormDataFile().setContentStream(inputStream));
            // method 2: use byte array
            reqCall.setImage(new FormDataFile().setContent(IOs.readAll(inputStream)));
            reqCall.setImageType("message");
            Response<Image> response = reqCall.execute();
            System.out.println(response.getRequestID());
            System.out.println(response.getHTTPStatusCode());
            System.out.println(Jsons.DEFAULT_GSON.toJson(response));
        }
    }

    private static void testGet() throws Exception {
        ImageService service = new ImageService(config);
        // method 1: write file
        try (FileOutputStream output = new FileOutputStream("test_download_img_2.png")) {
        // method 2: write byte buffer
        // try (ByteArrayOutputStream output = new ByteArrayOutputStream()) {
            ImageService.ImageGetReqCall reqCall = service.getImages().get();
            reqCall.setImageKey("img_89a76112-ab5d-46a6-b0a8-85255315c49g");
            reqCall.setResponseStream(output);
            Response response = reqCall.execute();
            System.out.println(Jsons.DEFAULT_GSON.toJson(response.getHeader()));
            System.out.println(response.getRequestID());
            System.out.println(response.getHTTPStatusCode());
            if (response.getHTTPStatusCode() == 200) {
                // method 1:
                // write file success

                // method 2: get byte array
                // byte[] bs  = output.toByteArray();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
