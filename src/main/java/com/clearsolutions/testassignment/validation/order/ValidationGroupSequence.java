package com.clearsolutions.testassignment.validation.order;

import javax.validation.GroupSequence;
import javax.validation.groups.Default;

@GroupSequence({Default.class, Secondary.class})
public interface ValidationGroupSequence {
}
