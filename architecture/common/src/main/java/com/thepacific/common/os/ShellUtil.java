package com.thepacific.common.os;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public final class ShellUtil {

    private ShellUtil() {
        throw new AssertionError("No instances");
    }

    public static ExcResult execCmd(String command, boolean isNeedMessage) {
        return execCmd(new String[]{command}, false, isNeedMessage);
    }

    public static ExcResult execCmd(String[] commands, boolean isRoot, boolean isNeedMessage) {
        if (commands == null || commands.length == 0) {
            return new ExcResult(-1, null);
        }
        Process process = null;
        BufferedReader successReader = null;
        BufferedReader errorReader = null;
        StringBuilder successMessage = null;
        StringBuilder errorMessage = null;
        DataOutputStream os = null;
        int excCode = -1;
        try {
            process = Runtime.getRuntime().exec(isRoot ? "su" : "sh");
            os = new DataOutputStream(process.getOutputStream());
            for (String command : commands) {
                if (command == null) continue;
                os.write(command.getBytes());
                os.writeBytes("\n");
                os.flush();
            }
            os.writeBytes("exit\n");
            os.flush();
            excCode = process.waitFor();
            if (isNeedMessage) {
                successMessage = new StringBuilder();
                errorMessage = new StringBuilder();
                successReader = new BufferedReader(new InputStreamReader(process.getInputStream(), "UTF-8"));
                errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream(), "UTF-8"));
                String s;
                while ((s = successReader.readLine()) != null) {
                    successMessage.append(s);
                }
                while ((s = errorReader.readLine()) != null) {
                    errorMessage.append(s);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (os != null) os.close();
                if (isNeedMessage) {
                    if (successReader != null) successReader.close();
                    if (errorReader != null) errorReader.close();
                }
            } catch (IOException ignored) {
                ignored.printStackTrace();
            }
            if (process != null) {
                process.destroy();
            }
        }

        String message;
        if (excCode == 0) {
            message = successMessage == null ? null : successMessage.toString();
        } else {
            message = errorMessage == null ? null : errorMessage.toString();
        }
        return new ExcResult(excCode, message);
    }

    public static class ExcResult {
        /**
         * @code 0 indicates normal termination.
         */
        public int code;
        public String message;

        public ExcResult(int code, String message) {
            this.code = code;
            this.message = message;
        }
    }
}