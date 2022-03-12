package site.metacoding.serverproject.web;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import site.metacoding.serverproject.domain.Hospital;
import site.metacoding.serverproject.domain.HospitalRepository;

@Controller
public class DownloadController {

    private HospitalRepository hospitalRepository;

    public DownloadController(HospitalRepository hospitalRepository) {
        this.hospitalRepository = hospitalRepository;
    }

    @GetMapping("/")
    public String main() {
        return "/download";
    }

    @GetMapping("hospital/download")
    public String Download(Model model) {

        StringBuffer sb = new StringBuffer();

        sb.append("http://3.38.254.72:5000/api/hospital?");
        sb.append("sidoCdNm=");
        sb.append("부산&");
        sb.append("sgguCdNm=");
        sb.append("부산사하구");

        System.out.println(sb.toString());

        RestTemplate restTemplate = new RestTemplate();
        Hospital[] response = restTemplate.getForObject(sb.toString(), Hospital[].class);

        List<Hospital> hospitals = Arrays.asList(response);
        hospitalRepository.saveAll(hospitals);

        model.addAttribute("hospital", hospitals);

        return "/hospital/list";
    }

    @GetMapping("hospital/list")
    public String show(Model model) {
        List<Hospital> hosEntity = hospitalRepository.findAll();

        model.addAttribute("hospitals", hosEntity);

        return "/list";
    }
}