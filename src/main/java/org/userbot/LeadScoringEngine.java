package org.userbot;

import org.userbot.dto.TelegramMessage;

public class LeadScoringEngine {

    public void setScores(TelegramMessage message){
        String text = message.getText();

        int potential = LeadAnalyzer.analyze(text);
        LeadClassifierClient pythonAnalyzer = new LeadClassifierClient();
        double probability = 0;
        try {
            probability = pythonAnalyzer.isLeadCheck(text);
        }catch (Exception e){
            System.out.println("Error while analyzing lead by ML model: " + e.getLocalizedMessage());
            probability = 0;
        }
        message.setLeadPotential(potential);
        message.setLeadProbability(probability);

    }
    public boolean isLead(TelegramMessage message){
        return message.getLeadProbability() > 0.8;
    }
}
