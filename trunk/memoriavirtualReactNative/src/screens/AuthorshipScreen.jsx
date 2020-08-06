import React from 'react';
import { StyleSheet, ScrollView } from 'react-native';

import generalinfoStyles from '../styles/generalinfo';

import Title from '../components/Title';
import AuthorshipCard from '../components/AuthorshipCard';

const styles = StyleSheet.create({
  ...generalinfoStyles(),
});

const AuthorshipScreen = ({ navigation }) => {
  return (
    <ScrollView contentContainerStyle={styles.generalPurposeForm}>
      <Title titleText="Sibipiruna" />
      <AuthorshipCard author="IBM" authorshipKind="Autor Institucional" />
      <AuthorshipCard author="IBM" authorshipKind="Autor Institucional" />
      <AuthorshipCard author="IBM" authorshipKind="Autor Institucional" />
      <AuthorshipCard author="IBM" authorshipKind="Autor Institucional" />
      <AuthorshipCard author="IBM" authorshipKind="Autor Institucional" />
      <AuthorshipCard author="IBM" authorshipKind="Autor Institucional" />
      <AuthorshipCard author="IBM" authorshipKind="Autor Institucional" />
    </ScrollView>
  );
};


AuthorshipScreen.navigationOptions = () => ({
  headerTitle: 'Autoria',
});

export default AuthorshipScreen;