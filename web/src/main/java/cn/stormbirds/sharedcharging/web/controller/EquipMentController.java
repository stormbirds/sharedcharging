package cn.stormbirds.sharedcharging.web.controller;

import cn.stormbirds.sharedcharging.api.equipment.IEquipmentControllerService;
import cn.stormbirds.sharedcharging.web.domain.ResultCode;
import cn.stormbirds.sharedcharging.web.domain.ResultJson;
import io.swagger.annotations.Api;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * cn.stormbirds.sharedcharging.web.controller
 * </p>
 *
 * @author StormBirds Email：xbaojun@gmail.com
 * @since 2019/12/30 9:21 上午
 */
@Api(tags = "设备操作Controller")
@RestController
@RequestMapping(value = "/app/v1/equipment")
public class EquipMentController {

    @Reference(version = "${equipment.service.version}")
    private IEquipmentControllerService equipmentControllerService;

    @GetMapping(value = "/scanQrCodeH5/{qrCode}")
    public String scanQrCodeH5(@PathVariable String qrCode){
        return "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>扫码</title>\n" +
                "    <script src=\"https://cdn.staticfile.org/jquery/1.10.2/jquery.min.js\"></script>\n" +
                "</head>\n" +
                "\n" +
                "<body>\n" +
                "    <button type=\"submit\" id=\"btn\" style=\"box-sizing: border-box;\n" +
                "    padding: 20px 100px;\n" +
                "    font-size: 18px;\n" +
                "    font-weight: 400;\n" +
                "    border-radius: 4px;\n" +
                "    color: #fff;\n" +
                "    background-color: #5cb85c;\n" +
                "    border-color: #4cae4c;\">确定</button>\n" +
                "    <script>\n" +
                "\n" +
                "        $(\"#btn\").click(function () {\n" +
                "            $.ajax({\n" +
                "                url: \"demo_test.txt\",\n" +
                "                success: function (result) {\n" +
                "                    alert(\"正确 \");\n" +
                "                }, error: function (xhr) {\n" +
                "                    alert(\"错误提示： \" + xhr.status + \" \" + xhr.statusText);\n" +
                "                }\n" +
                "            });\n" +
                "        });\n" +
                "    </script>\n" +
                "</body>\n" +
                "\n" +
                "</html>";
    }

    @PostMapping(value = "/rentPowerBank/{eqCode}")
    public ResultJson rentPowerBank(@PathVariable String eqCode){
        if(equipmentControllerService.ejectBatteryByDeviceId(eqCode)==1){
            return ResultJson.ok();
        }
        return ResultJson.failure(ResultCode.SERVER_ERROR);
    }
}
