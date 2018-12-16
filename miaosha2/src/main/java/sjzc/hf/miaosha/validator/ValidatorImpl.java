package sjzc.hf.miaosha.validator;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
public class ValidatorImpl implements InitializingBean {

	private static Validator validator;

	// 该方法用来校验
	public static ValidationResult validata(Object bean) {
		ValidationResult result = new ValidationResult();
		// 进行校验
		Set<ConstraintViolation<Object>> set = validator.validate(bean);

		// 判断是否有错
		if (set.size() > 0) {

			result.setHasErrors(true);
			// 便利并将错误信息进行存储
			set.forEach(s -> {
				// 得到错误信息
				String msg = s.getMessage();
				// 得到错误的字段名
				String name = s.getPropertyPath().toString();
				// 将名称与信息存入result中
				result.getErrorMsgMap().put(name, msg);
			});

		}

		return result;
	}

	@Override
	// 该方法实现在类生成后调用
	public void afterPropertiesSet() throws Exception {
		// 该方法实现在类生成后调用

		// 生成校验器
		validator = Validation.buildDefaultValidatorFactory().getValidator();

	}

}
