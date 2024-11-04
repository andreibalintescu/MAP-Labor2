package confectionery;

public class ConfectioneryController {

    private final ConfectioneryService confectioneryService ;

    public ConfectioneryController(ConfectioneryService confectioneryService) {
        this.confectioneryService = confectioneryService;
    }

    public void viewMenu() {
        StringBuilder output = new StringBuilder("Menu:\n");
        confectioneryService.getMenu().forEach(product -> output.append(product.toString()).append("\n"));
        System.out.println(output);
    }


//    public void viewStudents() {
//        StringBuilder output = new StringBuilder("Available students:\n");
//        universityService.getAllStudents().forEach(student -> output.append(student.toString()).append("\n"));
//        System.out.println(output);
//    }
//
//
//    public void viewEnrolled(Integer courseId) {
//        StringBuilder output = new StringBuilder("Enrolled students in the course:\n");
//        universityService.getEnrolledStudents(courseId).forEach(student -> output.append(student.toString()).append("\n"));
//        System.out.println(output);
//    }
//
//
//    public void deleteCourse(Integer courseId) {
//        universityService.removeCourse(courseId);
//        System.out.println("Removed course " + courseId);
//    }
}