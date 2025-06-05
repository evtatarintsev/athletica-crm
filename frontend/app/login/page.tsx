'use client';

import {Card, CardContent, CardDescription, CardFooter, CardHeader, CardTitle} from '@/components/ui/card';
import {Box, Button, CircularProgress, TextField} from "@mui/material";
import {Field, Form, Formik} from 'formik';
import * as Yup from 'yup';
import {useState} from 'react';
import {apiClient} from '@/lib/api-client';
import {useRouter} from 'next/navigation';
import 'client-only';

// Схема валидации для формы логина
const LoginSchema = Yup.object().shape({
  login: Yup.string()
    .required('Обязательное поле'),
  password: Yup.string()
    .required('Обязательное поле')
});

export default function LoginPage() {
  const [error, setError] = useState('');
  const [isLoading, setIsLoading] = useState(false);
  const router = useRouter();

  const handleSubmit = async (values: { login: string; password: string }, { setSubmitting }: { setSubmitting: (isSubmitting: boolean) => void }) => {
    setError('');
    setIsLoading(true);

    try {
      await apiClient.login({
        login: values.login,
        password: values.password
      });

      // Перенаправляем на главную страницу
      router.push('/');
    } catch (err) {
      setError('Неверный логин или пароль');
    } finally {
      setIsLoading(false);
      setSubmitting(false);
    }
  };

  return (
    <div className="min-h-screen flex justify-center items-start md:items-center p-8">
      <Card className="w-full max-w-sm">
        <CardHeader>
          <CardTitle className="text-2xl">Вход в систему</CardTitle>
          <CardDescription>
            Пожалуйста, введите свои учетные данные
          </CardDescription>
        </CardHeader>
        <CardContent>
          <Formik
            initialValues={{ login: '', password: '' }}
            validationSchema={LoginSchema}
            onSubmit={handleSubmit}
          >
            {({ isSubmitting, errors, touched }) => (
              <Form className="space-y-4">
                <div>
                  <Field
                    as={TextField}
                    name="login"
                    label="Логин"
                    variant="outlined"
                    fullWidth
                    error={touched.login && Boolean(errors.login)}
                    helperText={touched.login && errors.login}
                  />
                </div>
                <div>
                  <Field
                    as={TextField}
                    name="password"
                    label="Пароль"
                    type="password"
                    variant="outlined"
                    fullWidth
                    error={touched.password && Boolean(errors.password)}
                    helperText={touched.password && errors.password}
                  />
                </div>
                {error && (
                  <Box sx={{ color: 'error.main', mt: 2 }}>
                    {error}
                  </Box>
                )}
                <CardFooter className="px-0 pt-4">
                  <Button 
                    type="submit" 
                    variant="contained" 
                    color="primary" 
                    fullWidth 
                    disabled={isSubmitting || isLoading}
                  >
                    {isLoading ? <CircularProgress size={24} /> : 'Войти'}
                  </Button>
                </CardFooter>
              </Form>
            )}
          </Formik>
        </CardContent>
      </Card>
    </div>
  );
}
