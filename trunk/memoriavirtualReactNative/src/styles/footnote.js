import {
  heightPercentageToDP as hp,
} from 'react-native-responsive-screen';

const footnoteStyles = () => ({
  footnoteContainer: {
    position: 'absolute',
    bottom: 0,
    marginBottom: hp('2%'),
  },
  footnoteText: {
    fontSize: 14,
    textAlign: 'center',
  },
});

export default footnoteStyles;
