import React from "react";
import "./css/Button.css";

const STYLES = ["btn--submit", "btn--delete", "btn--update", "btn--normal"];
const SIZES = ["btn--medium", "btn--large", "btn--small"];

export const Button = ({ children, onClick, buttonStyle, buttonSize }) => {
  const checkButtonStyle = STYLES.includes(buttonStyle)
    ? buttonStyle
    : STYLES[0];

  const checkButtonSize = SIZES.includes(buttonSize) ? buttonSize : SIZES[0];

  return (
    <button
      className={`btn ${checkButtonStyle} ${checkButtonSize}`}
      onClick={onClick}
    >
      {children}
    </button>
  );
};
