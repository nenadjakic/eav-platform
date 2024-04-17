package com.github.nenadjakic.eav.validator

import com.github.nenadjakic.eav.dto.MinMaxRequest
import jakarta.validation.ConstraintValidator

class DoubleMinMaxValidator : ConstraintValidator<MinMax, MinMaxRequest<Double>?>, AbstractMinMaxValidator<Double>()