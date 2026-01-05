package com.doraemon.pencil;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.googleai.GoogleAiGeminiChatModel;
import java.time.Duration;

public class LogicEngine {
    
    private final ChatLanguageModel model = GoogleAiGeminiChatModel.builder()
            .apiKey("YOUR_GOOGLE_API_KEY_HERE") 
            .modelName("gemini-2.5-flash")
            .temperature(0.7)
            // Increase the allowed length of the response
            .maxOutputTokens(2000) 
            // Set all timeouts to 2 minutes for heavy reports
            .timeout(Duration.ofMinutes(2))
            .build();

    public String solveProblem(String prompt) {
        // We add a instruction to keep it stable
        String systemInstruction = "You are the 'Computer Pencil' gadget. " +
                                   "Provide a detailed, structured response for: ";
        try {
            return model.generate(systemInstruction + prompt);
        } catch (Exception e) {
            e.printStackTrace(); // This prints the full secret error in your CMD
            return "Error: The lead broke! The problem was too complex for the current connection. \nDetails: " + e.getMessage();
        }
    }
}