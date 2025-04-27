<div dir="rtl">

# סיכום מפגש ראשון והכנה למפגש השני - השתלמות Spring Boot

## סיכום מפגש ראשון

במפגש הקודם בנינו אפליקציית Spring Boot בצורה הדרגתית, החל משלד בסיסי ביותר ועד לארכיטקטורת MVC עם פונקציונליות CRUD מלאה:

### שלב 1: אפליקציה בסיסית

- יצרנו פרויקט Spring Boot בסיסי באמצעות Spring Initializer
- הכרנו את מבנה הפרויקט והתיקיות
- בחנו את המחלקה הראשית עם האנוטציה `@SpringBootApplication`
- ניתחנו את פלט ההרצה והבנו איך Spring Boot מפעיל שרת Tomcat מוטמע

</div>

```java
@SpringBootApplication
public class Stage1Application {
    public static void main(String[] args) {
        SpringApplication.run(Stage1Application.class, args);
    }
}
```

<div dir="rtl">

### שלב 2: הוספת קונטרולר בסיסי

- למדנו על תפקיד הקונטרולר ב-Spring MVC
- הכרנו את הזרימה של בקשות דרך DispatcherServlet
- יצרנו קונטרולר פשוט עם אנדפוינט אחד
- הכרנו אנוטציות בסיסיות: `@RestController`, `@RequestMapping`, `@GetMapping`

</div>

```java
@RestController
@RequestMapping("/welcome")
public class WelcomeController {
    @ResponseBody
    @RequestMapping("/greet")
    public String greet() {
        return "welcome to the world of spring boot";
    }
}
```

<div dir="rtl">

### שלב 3: ארכיטקטורת MVC בסיסית

- בנינו ארכיטקטורה שכבתית מלאה:
    - **מודל (Model)**: מחלקת `Student` המייצגת את הנתונים
    - **שירות (Service)**: `StudentService` המכיל את הלוגיקה העסקית
    - **בקר (Controller)**: `StudentController` לטיפול בבקשות HTTP
- למדנו על הזרקת תלויות (Dependency Injection) דרך קונסטרקטור
- הבנו את היתרונות של ארכיטקטורה שכבתית והפרדת אחריות

</div>

```java
@RestController
@RequestMapping("/student")
public class StudentController {
    private final StudentService studentServiceImpl;

    public StudentController(StudentService studentServiceImpl) {
        this.studentServiceImpl = studentServiceImpl;
    }

    @GetMapping("/getAllStudents")
    public List<Student> getAllStudents() {
        return studentServiceImpl.getAllStudents();
    }
}
```

<div dir="rtl">

### שלב 4: CRUD מלא ושיפורים ארכיטקטוניים

- שדרגנו את המערכת עם כל פעולות ה-CRUD:
    - **Create**: הוספת סטודנט
    - **Read**: קריאת רשימת סטודנטים
    - **Update**: עדכון סטודנט קיים
    - **Delete**: מחיקת סטודנט
- למדנו על ספריית Lombok להפחתת קוד שגרתי (boilerplate)
- הכרנו את היתרונות של הפרדה בין ממשק ומימוש
- הפרדנו את השירות לממשק `StudentService` ומימוש `StudentServiceImpl`

</div>

```java
public interface StudentService {
    List<Student> getAllStudents();
    String addStudent(Student student);
    String updateStudent(Student student);
    String deleteStudent(Long id);
}
```

<div dir="rtl">

## מה נלמד במפגש השני

במפגש הנוכחי נעלה רמה ונתמקד בשיפור האיכות והמקצועיות של ה-API שלנו, בסדר הבא:

### שלב 5: טיפול בחריגות ושיפור תגובות ה-API

1. **מבוא ל-ResponseEntity**
    - נלמד על הצורך ב-ResponseEntity לשליטה מלאה בתגובות HTTP
    - נבין את חשיבות קודי התגובה הנכונים (200, 201, 204, 400, 404)
    - נכיר את המבנה והשימושים של ResponseEntity

2. **יישום מעשי של ResponseEntity**
    - נעדכן את הקונטרולר שלנו לשימוש ב-ResponseEntity
    - נטפל בחריגות באופן מקומי (try-catch בכל מתודה)
    - נשפר את התגובות עם קודי סטטוס מתאימים וגוף תגובה עשיר יותר

3. **עיצוב API מקצועי**
    - נדון בעיצוב נכון של נתיבי API (endpoints) ברוח RESTful
    - נשפר את המיפוי בבקר ואת השימוש בפעולות HTTP

### שלב 5.0: מנגנון תגובות אחיד

נלמד כיצד ליצור מבנה תגובה אחיד עבור כל ה-API:
- נגדיר מחלקת `StandardResponse` לעטיפת כל התגובות
- ניצור `GlobalResponseHandler` שיטפל בעטיפת כל התגובות
- נלמד לטפל במקרים מיוחדים כמו תגובות 204 (No Content)

### שלב 5.1: טיפול גלובלי בחריגות

נשפר את הטיפול בחריגות:
- נלמד על `@ControllerAdvice` ו-`@ExceptionHandler`
- ניצור `GlobalExceptionHandler` לריכוז כל הטיפול בחריגות
- נשלב את הטיפול בחריגות עם מנגנון התגובות האחיד
- נלמד את היתרונות: ריכוז הטיפול בשגיאות, פישוט הקוד בבקרים

### שלב 5.2: ולידציה וטיפול בשגיאות ולידציה

נוסיף ולידציה לנתונים הנכנסים:
- נשלב Jakarta Bean Validation באמצעות אנוטציות במודל
- נוסיף `@Valid` לפרמטרים בבקר
- נרחיב את `GlobalExceptionHandler` לטיפול בשגיאות ולידציה
- נחזיר פרטי שגיאה עשירים עם מיפוי שדות שגויים

## הכנה לשלב 5: מבוא ל-ResponseEntity

לפני שנתחיל בכתיבת קוד, חשוב להבין למה בכלל אנחנו צריכים `ResponseEntity`:

### יתרונות השימוש ב-ResponseEntity

1. **שליטה מלאה בקודי סטטוס HTTP**:
    - לא רק 200 OK - אלא גם 201 Created, 204 No Content, 400 Bad Request, 404 Not Found ועוד
    - מאפשר תקשורת ברורה על סטטוס הפעולה

2. **הוספת כותרות HTTP לתגובה**:
    - הוספת מידע כמו Location לישויות חדשות
    - הגדרת סוג תוכן (Content-Type) וכותרות אחרות

3. **שיפור טיפול בשגיאות**:
    - החזרת קודי שגיאה מדויקים ומשמעותיים
    - מידע מפורט על סיבת השגיאה

4. **תאימות לסטנדרטים של REST**:
    - שימוש בכל מגוון הכלים שבפרוטוקול HTTP
    - API מקצועי יותר ומובן יותר לצרכנים

### דוגמאות בסיסיות לשימוש ב-ResponseEntity

</div>

```java
// החזרת סטטוס הצלחה עם גוף תגובה (200 OK)
@GetMapping("/students")
public ResponseEntity<List<Student>> getAllStudents() {
    List<Student> studentList = studentService.getAllStudents();
    return ResponseEntity.ok(studentList);
}

// החזרת סטטוס יצירה עם URI למשאב החדש (201 Created)
@PostMapping("/students")
public ResponseEntity<Student> addStudent(@RequestBody Student student) {
    Student added = studentService.addStudent(student);
    
    URI location = ServletUriComponentsBuilder
        .fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(added.getId())
        .toUri();
    
    return ResponseEntity.created(location).body(added);
}

// החזרת תגובה ללא תוכן (204 No Content)
@DeleteMapping("/students/{id}")
public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
    studentService.deleteStudent(id);
    return ResponseEntity.noContent().build();
}
```

<div dir="rtl">

## השלבים הבאים במפגש

במהלך המפגש נתקדם בהדרגה:

1. נתחיל מהוספת `ResponseEntity` ומחלקות חריגה ייעודיות - **שלב 5**
2. נמשיך למנגנון תגובות אחיד עם `StandardResponse` - **שלב 5.0**
3. נוסיף טיפול גלובלי בחריגות עם `@ControllerAdvice` - **שלב 5.1**
4. נסיים עם הוספת ולידציה וטיפול בשגיאות ולידציה - **שלב 5.2**

בכל שלב נדון ביתרונות הגישה החדשה ונבצע יישום מעשי בקוד.

</div>