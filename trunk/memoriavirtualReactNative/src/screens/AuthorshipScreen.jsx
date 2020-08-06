import React from 'react';
import { StyleSheet, View, ScrollView } from 'react-native';

import * as labels from '../constants/authorship';

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
  headerTitle: labels.AUTHORSHIP,
});

export default AuthorshipScreen;