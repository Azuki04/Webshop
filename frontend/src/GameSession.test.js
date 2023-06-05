import { fireEvent, render, screen } from '@testing-library/react';
import GameSession from './GameSession';


test("Questions and Answers are properly rendered", () => {
    // Arrange
    let questions = [
        {
          question: "Meine BeispielFrage",
          answers: ["Antwort 1", "Antwort 2", "Antwort 3"],
          correct_answer: "Antwort 1",
        }
    ]

    // Act
    render(
        <GameSession questions={ questions } />
    )

    // Assert
    expect(screen.getByText("Meine BeispielFrage")).toBeVisible()
    expect(screen.getByText("Antwort 1")).toBeVisible()
    expect(screen.getByText("Antwort 2")).toBeVisible()
    expect(screen.getByText("Antwort 3")).toBeVisible()
});

test("Selecting right answer displays success", () => {
    // Arrange
    let questions = [
        {
          question: "Meine BeispielFrage",
          answers: ["Antwort 1", "Antwort 2", "Antwort 3"],
          correct_answer: "Antwort 1",
        }
      ]

    // Act
    const {container} = render(
        <GameSession questions={ questions }/>
    );

    const button = screen.getByText("Antwort 1");
    fireEvent.click(button);

    // Assert
    expect(screen.getByText("Hurra")).toBeVisible()
});

test("Selecting wrong answer displays failure", () => {
    // Arrange
    let questions = [
        {
          question: "Meine BeispielFrage",
          answers: ["Antwort 1", "Antwort 2", "Antwort 3"],
          correct_answer: "Antwort 1",
        }
      ]

    // Act
    const {container} = render(
        <GameSession questions={ questions }/>
    );

    const button = screen.getByText("Antwort 2");
    fireEvent.click(button);

    // Assert
    expect(screen.getByText("Ohh nein")).toBeVisible()
});