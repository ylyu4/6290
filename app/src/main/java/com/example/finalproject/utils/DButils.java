package com.example.finalproject.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.finalproject.model.Cart;
import com.example.finalproject.model.Course;
import com.example.finalproject.model.Instructor;
import com.example.finalproject.model.User;

public class DButils extends SQLiteOpenHelper {
    //数据库版本号
    private static final int DATABASE_VERSION=1;

    //数据库名称
    private static final String DATABASE_NAME="project";

    public DButils(Context context){
        super(context,DATABASE_NAME,null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建数据表
        String CREATE_TABLE_USER="CREATE TABLE "+ User.TABLE + "("
                + User.KEY_username + " TEXT ,"
                + User.KEY_password + " TEXT ,"
                + User.KEY_registeredCourses + " TEXT )";
        String CREATE_TABLE_COURSE = "CREATE TABLE " + Course.TABLE + "("
                + Course.KEY_courseNumber + " TEXT ,"
                + Course.KEY_title + " TEXT ,"
                + Course.KEY_creditHours + " TEXT ,"
                + Course.KEY_description + " TEXT ,"
                + Course.KEY_crn + " TEXT ,"
                + Course.KEY_section + " TEXT ,"
                + Course.KEY_day + " TEXT ,"
                + Course.KEY_startTime + " TEXT ,"
                + Course.KEY_endTime + " TEXT ,"
                + Course.KEY_instructor + " TEXT ,"
                + Course.KEY_location + " TEXT ,"
                + Course.KEY_prerequisites + " TEXT )";
        String CREATE_TABLE_INSTRUCTOR = "CREATE TABLE " + Instructor.TABLE + "("
                + Instructor.KEY_name + " TEXT ,"
                + Instructor.KEY_phoneNumber + " TEXT ,"
                + Instructor.KEY_email + " TEXT ,"
                + Instructor.KEY_office + " TEXT ,"
                + Instructor.KEY_rateMyProfessorId + " TEXT )";
        String CREATE_TABLE_CART = "CREATE TABLE " + Cart.TABLE + "("
                + Cart.KEY_username + " TEXT ,"
                + Cart.KEY_courseInCart + " TEXT )";
        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_COURSE);
        db.execSQL(importCourseInfo());
        db.execSQL(CREATE_TABLE_INSTRUCTOR);
        db.execSQL(importInstructorInfo());
        db.execSQL(CREATE_TABLE_CART);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //如果旧表存在，删除，所以数据将会消失
        db.execSQL("DROP TABLE IF EXISTS "+ User.TABLE);
        db.execSQL("DROP TABLE IF EXISTS "+ Course.TABLE);
        db.execSQL("DROP TABLE IF EXISTS "+ Instructor.TABLE);
        db.execSQL("DROP TABLE IF EXISTS "+ Cart.TABLE);

        //再次创建表
        onCreate(db);
    }

    private String importCourseInfo() {
        return "Insert into " + Course.TABLE + " (" + Course.KEY_courseNumber + "," + Course.KEY_title + ","
                + Course.KEY_creditHours + "," + Course.KEY_description + "," + Course.KEY_crn + "," + Course.KEY_section + ","
                + Course.KEY_day + "," + Course.KEY_startTime + "," + Course.KEY_endTime + "," + Course.KEY_instructor + "," + Course.KEY_location + "," + Course.KEY_prerequisites + ") values " +
                "('3119', 'Introduction to Programming', '3', 'Introductory course in writing simple computer programs using Python; data-analytic thinking and business applications through hands-on practices. No prior knowledge or experience in programming is required.', '11765', '10', 'T/Th', '09:35', '10:50', 'Shivraj Kanungo', 'Duques-Bus Sch | Room 351', 'No'), " +
                "('4121', 'Database Design & Application', '3', 'Theory, architecture, and implementation of database management systems in corporate and organization information systems; fundamental concepts of database management and processing; hands-on experience with database management packages.', '11471', '10', 'M/W', '15:45', '17:00', 'Zhen Sun', 'Duques-Bus Sch | Room 352', 'No'), " +
                "('6200', 'Python Program Database Apps', '3', 'Introduction to Python programming language, Structured Query Language (SQL), relational database design, data wrangling, and rudimentary data analysis.', '17924', '10', 'Th', '19:10', '21:40', 'Yixin Lu', 'Funger Hall | Room 208', 'No'), " +
                "('6201', 'Info Syst Devel/Applications', '3', 'The information systems life cycle evaluated in terms of technologies, impact, and management. Structured and object-oriented analysis, prototyping, software reuse, testing, life-cycle costs, software development environments, and organizational and behavioral aspects of development projects. Restricted to students in the MS in information systems technology program or with permission of the department.', '12113', '10', 'M', '19:10', '21:40', 'Subhasish Dasgupta', 'Duques-Bus Sch | Room 354', 'No'), " +
                "('6202', 'Relational Databases', '3', 'Introduction to the theory of relational databases and commences an in-depth discussion of Relational database theory and design at the conceptual, logical, and physical levels. Structured query language (SQL) is covered in depth. Restricted to Students in the MS in Information Systems Technology program or with permission of the department.', '15956', '11', 'T', '19:10', '21:40', 'Young Ki Park', 'Duques-Bus Sch | Room 652', 'No'), " +
                "('6204', 'Information Tech Proj Mgt', '3', 'Project and program management practices with an emphasis on information technology projects. The basic tools of project management: work breakdown structure, cost, schedule and performance goal setting, and risk analysis. Restricted to students in the MS in information systems technology program or with permission of the department.', '12111', '10', 'Sa', '9:30', '12:00', 'Elias Carayannis', 'Duques-Bus Sch | Room 356', 'No'), " +
                "('6205', 'Web Application Development', '3', 'Concepts and practice for creating Internet content. Technical overview of the Internet environment and the structure of the World Wide Web. Design and implementation of an effective web site with HTML, CSS, and JavaScript. Restricted to students in the MS in information systems technology program or with permission of the department', '12112', '10', 'M', '16:30', '19:30', 'Wei Chen', 'Duques-Bus Sch | Room 355', 'undergraduate:ISTM 3119 D-, graduate:ISTM 6200 C'), " +
                "('6206', 'Information Systems Security', '3', 'Comprehensive examination of computer security issues from the design, management, and business information system ownership perspectives. System security concepts, methods, and policies from the design and planning stages to multi-level system implementation. Design of risk assessment strategies to achieve security goals. Restricted to students in the MS in information systems technology program or with permission of the department.', '15454', '11', 'W', '19:10', '21:40', 'Scott White', 'Funger Hall | Room 207', 'No'), " +
                "('6209', 'Web and Social Analytics', '3', 'Concepts, techniques, and tools of collecting, analyzing, and reporting digital data concerning how users interact with organizations through the Internet and social media; business intelligence; key performance indicators; new business models. Restricted to students in the MS in information systems technology program or with permission of the department.', 'lecture: 14672, discussion: 17212', 'ON & N30', 'Th', '19:30', '21:00', 'Wenjing Duan', 'Online courses | Room LINE', 'No'), " +
                "('6210', 'Info Syst Capstone', '3', 'Students apply conceptual and technical knowledge in analyzing, planning, and designing an online information system. Culminates with system proposal/design presentations. Restricted to students in their final semester in the MS in information systems technology program or with permission of the department.', '11903', '10', 'M', '16:30', '19:00', 'Subhasish Dasgupta', 'Duques-Bus Sch | Room 251', 'ISTM 6201: C, ISTM 6202: C, ISTM 6204: C, ISTM 6205: C, ISTM 6206: C, ISTM 6209: C'), " +
                "('6217', 'IoT Management', '3', 'Technology skills to analyze huge data sets using Apache Spark. Taught in Python, continuing on to learning to use Spark DataFrames. Using the MLlib machine learning library with the DataFrame syntax and Spark. Recommended background: Prior completion of ISTM 6214.', '18097', '80', 'W', '16:30', '19:00', 'Wei Chen', 'Duques-Bus Sch | Room 651', 'undergraduate:ISTM 3119 D-, graduate:ISTM 6200 C'), " +
                "('6218', 'Bus Apps of AI', '3', 'Comprehensive introduction to recent developments in artificial intelligence (AI) through the coverage of fundamental AI concepts, practical business applications, and hands-on experiences with modern deep learning frameworks.', '18098', '10', 'M', '19:10', '21:40', 'Wei Chen', 'Duques-Bus Sch | Room 252', 'ISTM 6214: C'), " +
                "('6222', 'IS/IT Strategy', '3', 'The development and implementation of information systems and technology strategies designed to align with and maximize business strategy applications and approaches in a challenging and increasingly global business environment.', '15957', '10', 'W', '19:10', '21:40', 'Young Ki Park', 'Duques-Bus Sch | Room 253', 'No'), " +
                "('6223', 'Technology Entrepreneurship', '3', 'Case studies on the innovation–entrepreneurship processes used to launch and build new ventures based on information technology and on technology more broadly. Organizing for innovation, raising venture capital, managing the small technology-based venture, marketing technology products and services, intellectual property considerations, and new venture proposal development.', '15613', '80', 'T', '19:10', '21:40', 'Richard Donnelly', 'Online courses | Room LINE', 'No'), " +
                "('6225', 'Cloud Foundations', '3', 'Concepts of cloud managed enterprise architecture as a management tool to align information technology assets, people, operations, and projects with operational characteristics.', '16378', '10', 'W', '19:10', '21:40', 'Anya Mendenhall', 'Duques-Bus Sch | Room 250', 'No'), " +
                "('6233', 'Emerging Technologies', '3', 'Developments in scientific and technological innovation, including automation, energy, medicine, bioengineering, social science, information technology, and space. Forecasting technological advances and assessing their economic and social effects.', 'lecture:15782, discussion:18323', 'ON & N30', 'Su', '9:30', '10:30', 'Elias Carayannis', 'Duques-Bus Sch | Room 353', 'No')";

    }

    private String importInstructorInfo() {
        return "Insert into " + Instructor.TABLE + " (" + Instructor.KEY_name + "," + Instructor.KEY_phoneNumber + "," + Instructor.KEY_email + "," + Instructor.KEY_office + "," + Instructor.KEY_rateMyProfessorId + ") values " +
                "('Shivraj Kanungo', '202-994-3734', 'kanungo@gwu.edu', 'DUQUES 515E', '2643395'), " +
                "('Zhen Sun', '202-994-4919', 'zhens@gwu.edu', 'Funger511', '2328578'), " +
                "('Yixin Lu', '202-994-4364', 'yixinlu@gwu.edu', 'Funger 513', '2346254'), " +
                "('Subhasish Dasgupta', '202-994-7408', 'dasgupta@gwu.edu', 'Funger 515B', null), " +
                "('Young Ki Park', '202-994-9581', 'ykpark@gwu.edu', 'Funger 515E', '2154607'), " +
                "('Elias Carayannis', '202-873-0262', 'caraye@gwu.edu', 'DUQUES 515C', '314719'), " +
                "('Wei Chen', '301-693-2005', 'wei_chen@gwu.edu', 'Funger512', '2601257'), " +
                "('Scott White', '571-553-5020', 'cybersec@gwu.edu', 'DUQUES 515D', '2298256'), " +
                "('Wenjing Duan', '202-994-3217', 'wduan@gwu.edu', 'Funger 515F', '1025041'), " +
                "('Richard Donnelly', '202-994-7155', 'rgd@gwu.edu', 'DUQUES 515A', '527955'), " +
                "('Anya Mendenhall', '571-665-8024', 'agueniss@gwu.edu', 'DUQUES 515B', '2539797')";
    }




}