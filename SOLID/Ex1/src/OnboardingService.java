import java.util.*;

public class OnboardingService {
    private final StudentRepository db;
    private final ConfirmationPrinter printer;

    public OnboardingService(StudentRepository db) {
        this.db = db;
        this.printer = new ConfirmationPrinter();
    }

    public void registerFromRawInput(String raw) {
        System.out.println("INPUT: " + raw);

        ParseString ps = new ParseString();
        Map<String,String> kv = ps.parser(raw);

        String name = kv.getOrDefault("name", "");
        String email = kv.getOrDefault("email", "");
        String phone = kv.getOrDefault("phone", "");
        String program = kv.getOrDefault("program", "");

        Validate studentValidate = new Validate();
        List<String> errors = studentValidate.validate(kv);

        ErrorPrinter er = new ErrorPrinter();
        if (er.error(errors)) {
            return;
        }

        String id = IdUtil.nextStudentId(db.count());
        StudentRecord rec = new StudentRecord(id, name, email, phone, program);

        db.save(rec);
        printer.print(rec, db.count());
    }
}
