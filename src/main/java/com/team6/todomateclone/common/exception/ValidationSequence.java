package com.team6.todomateclone.common.exception;

import javax.validation.GroupSequence;
import javax.validation.groups.Default;

@GroupSequence({Default.class,
        ValidationGroups.EmailNotEmptyGroup.class,
        ValidationGroups.EmailPatternCheckGroup.class,
        ValidationGroups.PasswordNotEmptyGroup.class,
        ValidationGroups.PasswordPatternCheckGroup.class,
        ValidationGroups.NotEmptyGroup.class,
        ValidationGroups.SizeCheckGroup.class})
public interface ValidationSequence {
}
