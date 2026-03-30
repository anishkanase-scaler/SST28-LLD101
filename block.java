Verify each finding against the current code and only fix it if needed.

In `@frontend/components/pages/QuizBuilder/QuizBuilderEdit.module.css` around
lines 130 - 137, The current CSS applies disabled affordances directly to base
classes (e.g., .editIcon and the other class block near lines 266–273), making
enabled controls appear disabled; change the rules so cursor: not-allowed,
opacity, and any other disabled visuals are only applied when the element is
actually disabled (use the :disabled pseudo-class or a .disabled modifier on the
same selectors like .editIcon:disabled and the corresponding selector at
266–273), and remove those properties from the base selector so hover/active
states (e.g., .editIcon:hover used by QuestionEditor.tsx) render correctly.