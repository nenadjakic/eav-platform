package com.github.nenadjakic.eav.validator

import com.github.nenadjakic.eav.dto.MinMaxRequest
import jakarta.validation.ConstraintValidator

class IntMinMaxValidator: ConstraintValidator<MinMax, MinMaxRequest<Int>?>, AbstractMinMaxValidator<Int>()