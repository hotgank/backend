package org.example.backend.service.serviceImpl.doctor;

import org.example.backend.entity.doctor.Doctor;
import org.example.backend.entity.others.Hospital;
import org.example.backend.mapper.doctor.DoctorMapper;
import org.example.backend.mapper.others.HospitalMapper;
import org.example.backend.service.doctor.DoctorService;
import org.example.backend.service.doctor.ImportDoctorService;
import org.example.backend.util.EncryptionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

@Service
public class ImportDoctorServiceImpl implements ImportDoctorService {

    private static final Logger logger = LoggerFactory.getLogger(ImportDoctorServiceImpl.class);

    @Autowired
    private DoctorMapper doctorMapper;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private EncryptionUtil encryptionUtil;

    @Autowired
    HospitalMapper hospitalMapper;

    @Override
    public boolean insertAllDoctors(List<Doctor> doctors) {
        try {
            for (Doctor doctor : doctors) {
                String doctorId = insertSingleLine(doctor);
                if(doctorId != null){
                    logger.info("Doctor {} inserted successfully", doctorId);
                }
            }
            logger.info("All doctors inserted successfully");
            return true;
        } catch (Exception e) {
            logger.error("Error inserting doctors: {}", e.getMessage(), e);
            return false;
        }
    }

    public String insertSingleLine(Doctor doctor) {
        try {
            if (!validateName(doctor)) return null;
            if (!validateUsername(doctor)) return null;
            if (!validatePassword(doctor)) return null;
            if (!validatePhone(doctor)) return null;
            if (!validateEmail(doctor)) return null;
            if (!validateGender(doctor)) return null;
            if (!validateWorkplace(doctor)) return null;

            String phone = convertScientificNotation(doctor.getPhone());
            doctor.setPhone(phone);

            setDefaultValues(doctor);
            String doctorId = generateDoctorId();
            doctor.setDoctorId(doctorId);

            doctorMapper.insertDoctor(doctor);
            return doctorId;
        } catch (Exception e) {
            logger.error("导入医生：{} 失败 {}", doctor.getDoctorId(), e.getMessage(), e);
            return null;
        }
    }

    private boolean validateName(Doctor doctor) {
        String name = doctor.getName();
        if (name == null) {
            logger.error("导入医生：{} 失败, 姓名不能为空", doctor.getDoctorId());
            return false;
        }
        if (name.length() > 20) {
            logger.error("导入医生：{} 失败, 姓名不能超过20个字符: {}", doctor.getDoctorId(), name);
            return false;
        }
        return true;
    }

    private boolean validateUsername(Doctor doctor) {
        String username = doctor.getUsername();
        if (username == null) {
            logger.error("导入医生：{} 失败, 用户名不能为空", doctor.getDoctorId());
            return false;
        }
        if (doctorService.isUsernameExist(username) != null) {
            logger.error("导入医生：{} 失败, 用户名已被注册: {}", doctor.getDoctorId(), username);
            return false;
        }
        if (username.length() > 20) {
            logger.error("导入医生：{} 失败, 用户名不能超过20个字符: {}", doctor.getDoctorId(), username);
            return false;
        }
        return true;
    }

    private boolean validatePassword(Doctor doctor) {
        String password = doctor.getPassword();
        if (password == null) {
            logger.error("导入医生：{} 失败, 密码不能为空", doctor.getDoctorId());
            return false;
        }
        if (!Pattern.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{10,16}$", password)) {
            logger.error("导入医生：{} 失败, 密码 {} 不符合要求：长度应在10-16位之间，且包含大小写字母和数字", doctor.getDoctorId(), password);
            return false;
        }
        password = encryptionUtil.encryptMD5(password);
        doctor.setPassword(password);
        return true;
    }

    private boolean validatePhone(Doctor doctor) {
        String phone = doctor.getPhone();
        if (phone == null) return true; // 手机号可以为空
        phone = convertScientificNotation(phone);
        try {
                if (!phone.matches( "^[0-9]+$")) {
                    logger.error("导入医生：{} 失败, 手机号码只能包含数字: {}", doctor.getDoctorId(), phone);
                    return false;
                }
                if (phone.length() > 11) {
                logger.error("导入医生：{} 失败, 手机号码长度不能超过11个数字: {}", doctor.getDoctorId(), phone);
                return false;
            }
        } catch (NumberFormatException e) {
            logger.error("导入医生：{} 失败, 手机号码格式不正确: {}", doctor.getDoctorId(), phone);
            return false;
        }
        return true;
    }

    private boolean validateEmail(Doctor doctor) {
        String email = doctor.getEmail();
        if (email == null) {
            logger.error("导入医生：{} 失败, 邮箱不能为空", doctor.getDoctorId());
            return false;
        }
        if (!Pattern.matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$", email)) {
            logger.error("导入医生：{} 失败, 邮箱格式不正确: {}", doctor.getDoctorId(), email);
            return false;
        }
        if (doctorService.isEmailExist(email) != null) {
            logger.error("导入医生：{} 失败, 邮箱已被注册: {}", doctor.getDoctorId(), email);
            return false;
        }
        return true;
    }

    private boolean validateGender(Doctor doctor) {
        String gender = doctor.getGender();
        if (!gender.equals("男") && !gender.equals("女") && !gender.equals("其他")) {
            doctor.setGender("未知");
        }
        return true;
    }

    private boolean validateWorkplace(Doctor doctor) {
        String workplace = doctor.getWorkplace();
        if (workplace == null) {
            logger.error("导入医生：{} 失败, 工作地点不能为空", doctor.getDoctorId());
            return false;
        }
        List<Hospital> hospitals = hospitalMapper.selectAll();
        boolean hospitalExists = hospitals.stream()
                .anyMatch(hospital -> hospital.getHospitalName().equals(workplace));
        if (!hospitalExists) {
            logger.error("导入医生：{} 失败, 工作地点 {} 不存在于医院列表中", doctor.getDoctorId(), workplace);
            return false;
        }
        return true;
    }

    public String convertScientificNotation(String phone) {
        if (phone == null || phone.isEmpty()) {
            return phone;
        }

        int eIndex = phone.indexOf('E');
        if (eIndex == -1) {
            return phone; // 如果没有E，直接返回原字符串
        }

        // 截取E之前的字符作为浮点数x
        String xStr = phone.substring(0, eIndex);
        double x = Double.parseDouble(xStr);

        // 截取E之后的字符作为整数y
        String yStr = phone.substring(eIndex + 1);
        int y = Integer.parseInt(yStr);

        // 计算z = x * 10^y
        long z = (long) (x * Math.pow(10, y));

        // 将z转换为字符串并返回
        return String.valueOf(z).replace(".", "");
    }

    private void setDefaultValues(Doctor doctor) {
        doctor.setQualification("未认证");
        doctor.setRating(-1);
        doctor.setAvatarUrl(null);
        doctor.setRegistrationDate(LocalDateTime.now());
        doctor.setLastLogin(null);
        doctor.setStatus("active");
    }

    private String generateDoctorId() {
        return "D-" + UUID.randomUUID();
    }

}
