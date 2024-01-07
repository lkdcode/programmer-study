package practice.baseball;

class Application {

    public static void main(String[] args) {
        final Computer computer = new Computer();
        final Parser parser = new Parser();
        final Referee referee = new Referee();

        System.out.println("숫자를 입력하세요");
        final String readLine = Reader.readLine();

        final BaseBallNumbers computerNumbers = computer.getNumbers();
        final BaseBallNumbers userNumbers = parser.parseInt(readLine);

        final GameResult gameResult = referee.makeJudgment(computerNumbers, userNumbers);
        System.out.println("computerNumber: " + computerNumbers);
        System.out.println("결과 : " + gameResult.strikeCount() + "S, " + gameResult.ballCount() + "B");
    }

}
