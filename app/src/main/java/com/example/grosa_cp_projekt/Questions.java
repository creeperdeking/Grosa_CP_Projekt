package com.example.grosa_cp_projekt;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

public class Questions {
    static class Question{
        public String question;
        public String good_answer;
        public ArrayList<String> other_answers;

        public Question(String question, String good_answer, String... other_answers) {
            this.question = question;
            this.good_answer = good_answer;
            this.other_answers = new ArrayList<>(Arrays.asList(other_answers));
        }

        public ArrayList<String> getQuestionsRandom() {
            ArrayList<String> answers = new ArrayList<>(other_answers);
            answers.add(good_answer);
            Collections.shuffle(answers);
            return answers;
        }

        public Integer getNbQuestion() {
            return other_answers.size() + 1;
        }
            
    }
    
    static private HashMap<ChooseDifficulty.Difficulty, ArrayList<Question>> questionsHistory = null;
    static private HashMap<ChooseDifficulty.Difficulty, ArrayList<Question>> questionsGeography = null;
    static private HashMap<ChooseDifficulty.Difficulty, ArrayList<Question>> questionsEnglish = null;

    static public ArrayList<Question> getQuestions(QuestionsActivity.Topic topic, ChooseDifficulty.Difficulty difficulty) {
        switch (topic) {
            case English:
                return getQuestionsEnglish(difficulty);
            case History:
                return getQuestionsHistory(difficulty);
            case Geography:
                return getQuestionsGeography(difficulty);
                default:
                    System.out.println("Nani???");
                    return null;
        }
    }

    static private ArrayList<Question> getQuestionsGeography(ChooseDifficulty.Difficulty difficulty) {
        if (questionsGeography == null) {
            questionsGeography = new HashMap<>();
            ArrayList<Question> questionsGeographyEasy = new ArrayList<>();
            questionsGeographyEasy.add(new Question("Combien y a-t-il de continents sur Terre?",
                    "5",
                    "4",
                    "2"));
            questionsGeographyEasy.add(new Question("Quel est la forme de la terre?",
                    "Une Sphère",
                    "Un disque",
                    "Un disque bombé"));
            questionsGeographyEasy.add(new Question("Laquelle de ces planètes n'existe pas?",
                    "Coruscant",
                    "Mars",
                    "Pluton"));
            questionsGeographyEasy.add(new Question("Quel est le plus grand océan du monde?",
                    "L'océan Pacifique",
                    "L'océan Atlantique",
                    "L'océan Méditerranéen"));
            questionsGeographyEasy.add(new Question("Quel est le plus haut sommet du monde?",
                    "Le mont Everest",
                    "Le mont Blanc",
                    "Le mont Kilimanjaro"));
            questionsGeography.put(ChooseDifficulty.Difficulty.EASY, questionsGeographyEasy);

            ArrayList<Question> questionsGeographyNormal = new ArrayList<>();
            questionsGeographyNormal.add(new Question("Combien y a-t-il d'étoiles dans la Galaxie?",
                    "~200 Milliards",
                    "~200 000",
                    "~ 350 Millions"));
            questionsGeographyNormal.add(new Question("Combien de temps la lumière met-elle pour atteindre la terre depuis le soleil?",
                    "8 minutes",
                    "Elle arrive instantanément",
                    "7 secondes"));
            questionsGeographyNormal.add(new Question("Quelle est la plus grande ville du monde?",
                    "Tokyo",
                    "Mexico City",
                    "New York"));
            questionsGeographyNormal.add(new Question("Quel est le pays avec la plus grosse économie du monde?",
                    "Les Etats Unis",
                    "La Chine",
                    "L'Allemagne"));
            questionsGeographyNormal.add(new Question("Quel est le plus long fleuve du monde?",
                    "L'Amazone",
                    "Le Nil",
                    "Le Mississipi"));
            questionsGeography.put(ChooseDifficulty.Difficulty.NORMAL, questionsGeographyNormal);

            ArrayList<Question> questionsGeographyHard = new ArrayList<>();
            questionsGeographyHard.add(new Question("A quelle altitude commence l'espace",
                    "~100km",
                    "8000km",
                    "~30feets"));
            questionsGeographyHard.add(new Question("Où se trouve le siège des Nations Unies?",
                    "A New York",
                    "A Genève",
                    "A Tokyo"));
            questionsGeographyHard.add(new Question("Quel pays ne fait pas partie du G7?",
                    "La Russie",
                    "L'Union Européenne",
                    "L'Italie"));
            questionsGeographyHard.add(new Question("Combien de membres compte l'Union Européenne?",
                    "26/27",
                    "18/19",
                    "34/35"));
            questionsGeographyHard.add(new Question("Combien de membres compte l'Union Européenne?",
                    "26/27",
                    "18/19",
                    "24/25"));
            questionsGeography.put(ChooseDifficulty.Difficulty.HARD, questionsGeographyHard);
        }
        return questionsGeography.get(difficulty);
    }

    static private ArrayList<Question> getQuestionsHistory(ChooseDifficulty.Difficulty difficulty) {
        if (questionsHistory == null) {
            questionsHistory = new HashMap<>();
            ArrayList<Question> questionsHistoryEasy = new ArrayList<>();
            questionsHistoryEasy.add(new Question("Combien y a-t-il eu de guerres mondiales?",
                    "Deux",
                    "Une",
                    "Trois"));
            questionsHistoryEasy.add(new Question("Quand l'homme a-t-il posé le pied sur la lune pour la première fois?",
                    "Il y a environ 50 ans",
                    "Il y a environ 200 ans",
                    "Jamais"));
            questionsHistoryEasy.add(new Question("Qui de ces trois hommes est un scientifique célèbre?",
                    "Albert Einstein",
                    "Jean Moulin",
                    "José Bové"));
            questionsHistoryEasy.add(new Question("Qui fût le premier empereur des Français?",
                    "Napoléon",
                    "Palpatine",
                    "Louis XIV"));
            questionsHistoryEasy.add(new Question("Quel fût la première démocratie moderne?",
                    "Les Etats Unis",
                    "La France",
                    "La Grèce Antique"));
            questionsHistory.put(ChooseDifficulty.Difficulty.EASY, questionsHistoryEasy);

            ArrayList<Question> questionsHistoryNormal = new ArrayList<>();
            questionsHistoryNormal.add(new Question("Quel est le nom de la période caractérisée par la maitrise de la vapeur?",
                    "La révolution Industrielle",
                    "La Renaissance",
                    "L'ère Steampunk"));
            questionsHistoryNormal.add(new Question("Quel est le plus célèbre des Empereurs Romains?",
                    "Jules César",
                    "Romulus",
                    "Caligula"));
            questionsHistoryNormal.add(new Question("Quel est l'âge de l'univers?",
                    "13,6 Milliards d'années",
                    "4,8 Milliards d'année",
                    "7000 ans"));
            questionsHistoryNormal.add(new Question("Les dinosaurs se sont éteint il y a environ?",
                    "60 Millions d'années",
                    "2 Milliards d'année",
                    "8000 ans"));
            questionsHistoryNormal.add(new Question("Durant la seconde guerre mondiale, qui dirigeait le Royaume Uni?",
                    "Winston Churchill",
                    "Joseph Chamberlain",
                    "Adolf Hitler"));
            questionsHistory.put(ChooseDifficulty.Difficulty.NORMAL, questionsHistoryNormal);

            ArrayList<Question> questionsHistoryHard = new ArrayList<>();
            questionsHistoryHard.add(new Question("Combien de fois les hommes sont ils allés sur la lune?",
                    "Six fois",
                    "Une fois",
                    "Onze fois"));
            questionsHistoryHard.add(new Question("Durant la seconde guerre mondiale, combien de juifs ont-il péri du fait des crimes de guerre Nazis?",
                    "~6 Millions",
                    "~ 11 Millions",
                    "~15 000"));
            questionsHistoryHard.add(new Question("Quelle est la date de la révolution Française",
                    "1789",
                    "1793",
                    "2022"));
            questionsHistoryHard.add(new Question("Sur quelle ville a été lachée la bombe atomique fût elle utilisée pour la première fois?",
                    "Hiroshima",
                    "Nagasaki",
                    "New Vegas"));
            questionsHistoryHard.add(new Question("Quel est le nom du traité qui mit fin à la première Guerre Mondiale?",
                    "Le traité de Versaille",
                    "Le traité de Maastricht",
                    "Le traité de paix"));
            questionsHistory.put(ChooseDifficulty.Difficulty.HARD, questionsHistoryHard);
        }
        return questionsHistory.get(difficulty);
    }

    static private ArrayList<Question> getQuestionsEnglish(ChooseDifficulty.Difficulty difficulty) {
        if (questionsEnglish == null) {
            questionsEnglish = new HashMap<>();
            ArrayList<Question> questionsEnglishEasy = new ArrayList<>();
            questionsEnglishEasy.add(new Question("Lequel de ces mots est il écrit correctement?",
                    "commencé",
                    "paralèle",
                    "Au jour d'hui"));
            questionsEnglishEasy.add(new Question("Lequel de ces mots n'existe pas?",
                    "Synacdote",
                    "Patronyme",
                    "Antédiluvien"));
            questionsEnglishEasy.add(new Question("Quel est l'auteur du \"Tour du monde en 80 jours?\"",
                    "Jules Vernes",
                    "Jules Ferry",
                    "Victor Hugo"));
            questionsEnglishEasy.add(new Question("Quel est le pluriel de portail?",
                    "Portails",
                    "Portaux",
                    "Portos"));
            questionsEnglishEasy.add(new Question("Comment appelle-t-on l'habillage d'une phrase?",
                    "La ponctuation",
                    "La casse",
                    "La ponctualité"));
            questionsEnglish.put(ChooseDifficulty.Difficulty.EASY, questionsEnglishEasy);

            ArrayList<Question> questionsEnglishNormal = new ArrayList<>();
            questionsEnglishNormal.add(new Question("Les chausettes de l'archiduchesse sont-elles?",
                    "Archi-Sèches",
                    "Sèces",
                    "Plutôt humides"));
            questionsEnglishNormal.add(new Question("Quel outil de jardinage désigne aussi une déception amoureuse?",
                    "Le rateau",
                    "La bêche",
                    "La faucille"));
            questionsEnglishNormal.add(new Question("L'oeuf ou la poule",
                    "La poule",
                    "L'oeuf",
                    "Ne se prononce pas"));
            questionsEnglishNormal.add(new Question("P=NP?",
                    "Non",
                    "Oui",
                    "Peut-être"));
            questionsEnglishNormal.add(new Question("Ce questionnaire est-t-il toujours objectif?",
                    "Oui",
                    "Non"));
            questionsEnglish.put(ChooseDifficulty.Difficulty.NORMAL, questionsEnglishNormal);

            ArrayList<Question> questionsEnglishHard = new ArrayList<>();
            questionsEnglishHard.add(new Question("Faut-il être prêt à mourir pour vivre?",
                    "Oui",
                    "Non",
                    "C'est compliqué..."));
            questionsEnglishHard.add(new Question("L'homme a-t-il découvert, ou bien créé dieu?",
                    "C'est compliqué...",
                    "Il l'a découvert",
                    "Il l'a créé"));
            questionsEnglishHard.add(new Question("L'homme a-t-il besoin d'un sens transcendant pour vivre pleinement?",
                    "Oui",
                    "Non",
                    "J'en ai marre que le questionnaire me dise ce que je dois penser"));
            questionsEnglishHard.add(new Question("La démocratie est-elle la pire forme de gouvernement?",
                    "Oui, à l'exception de tous les autres",
                    "Oui",
                    "A-t-on jamais vécu en démocratie?"));
            questionsEnglishHard.add(new Question("L'islam est-il compatible avec l'existence d'un état Laïque?",
                    "Pourvu que oui",
                    "Probablement pas",
                    "What?"));
            questionsEnglish.put(ChooseDifficulty.Difficulty.HARD, questionsEnglishHard);
        }

        return questionsEnglish.get(difficulty);
    }
}
