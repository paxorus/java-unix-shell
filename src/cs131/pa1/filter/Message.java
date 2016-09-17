package cs131.pa1.filter;

public enum Message {
    WELCOME("Welcome to the Unix-ish command line.\n"),
    NEWCOMMAND("> "),
    GOODBYE("Thank you for using the Unix-ish command line. Goodbye!\n"),
    FILE_NOT_FOUND("At least one of the files in the command [%s] was not found.\n"),
    DIRECTORY_NOT_FOUND("The directory specified by the command [%s] was not found.\n"),
    COMMAND_NOT_FOUND("The command [%s] was not recognized.\n"),
    REQUIRES_INPUT("The command [%s] requires input.\n"),
    CANNOT_HAVE_OUTPUT("The command [%s] cannot have an output.\n"),
    REQUIRES_PARAMETER("The command [%s] requires parameter(s).\n"),
    CANNOT_HAVE_INPUT("The command [%s] cannot have an input.\n"),
    INVALID_PARAMETER("The parameter for command [%s] is invalid.\n")
    ;
    private final String message;
    
    private Message(String message){
        this.message=message;
    }
    public String toString(){
        return this.message;
    }
    public String with_parameter(String parameter){
        if(this==WELCOME || this==NEWCOMMAND || this == GOODBYE){
            return this.toString();
        }
        return String.format(this.message, parameter.trim());
    }
    
}
