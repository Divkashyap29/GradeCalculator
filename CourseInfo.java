 
 public  class CourseInfo {
        public String courseName;
        public String grade;
        public double gradeValue;
        public int credits;

        public CourseInfo(String courseName, String grade, double gradeValue, int credits) {
            this.courseName = courseName;
            this.grade = grade;
            this.gradeValue = gradeValue;
            this.credits = credits;
        }

        public String getCourseName() {
            return courseName;
        }

        public String getGrade() {
            return grade;
        }

        public double getGradeValue() {
            return gradeValue;
        }

        public int getCredits() {
            return credits;
        }
    }

