package net.njay.mesigi.util.cmd;

public class CmdUtil {

    public static String getStringAttribute(String[] args, String... aliases){
        for (int i = 0; i < args.length; i++){
            for (String alias : aliases){
                if (args[i].equals(alias) && i+1<args.length)
                    return args[i+1];
            }
        }
        return null;
    }

    public static int getIntAttribute(String[] args, String... aliases){
        return Integer.valueOf(getStringAttribute(args, aliases));
    }

    public static double getDoubleAttribute(String[] args, String... aliases){
        return Double.valueOf(getStringAttribute(args, aliases));
    }

    public static long getLongAttribute(String[] args, String... aliases){
        return Long.valueOf(getStringAttribute(args, aliases));
    }

    public static float getFloatAttribute(String[] args, String... aliases){
        return Float.valueOf(getStringAttribute(args, aliases));
    }

}
